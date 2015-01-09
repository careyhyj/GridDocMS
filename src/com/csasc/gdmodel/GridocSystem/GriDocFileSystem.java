/**
 *
 */
package com.csasc.gdmodel.GridocSystem;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.apache.hadoop.fs.BufferedFSInputStream;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FSInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Syncable;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.util.Progressable;

import com.csasc.gdengine.paragraphengine.core.ParagraphEngine;
import com.csasc.gdmodel.datamodel.DataType;
import com.csasc.gdmodel.datamodel.DocumentType;
import com.csasc.gdmodel.datamodel.GriDocData;
import com.csasc.gdmodel.datamodel.Paragraph;
import com.csasc.gdmodel.datamodel.Section;
import com.csasc.gdmodel.dataoperation.GDManager;
import com.csasc.gdmodel.dataoperation.GriDoc;
import com.csasc.gdmodel.dataoperation.GriDocNamesystem;

/**
 * @author yuppie 代表一个PE上的格文档文件系统，负责实现对本地格文档的管理和存储。
 */
public class GriDocFileSystem extends FileSystem {
	private Path workingDirectory;

	private URI uri;
	public GriDocNamesystem namesystem;

	public GriDocFileSystem() {
		this.namesystem = new GriDocNamesystem(this,
				this.workingDirectory.toString());
	}

	public class GDFSInputStream extends FSInputStream {
		FileInputStream fin;
		private long position;

		public GDFSInputStream(GriDoc gridoc, Path f) throws IOException {
			this.fin = new LocatingParagraphFileInputStream(pathToFile(gridoc,
					f));
		}

		public void seek(long pos) throws IOException {
			fin.getChannel().position(pos);
			this.position = pos;
		}

		public long getPos() throws IOException {
			return this.position;
		}

		public boolean seekToNewSource(long targetPos) throws IOException {
			return false;
		}

		public int available() throws IOException {
			return fin.available();
		}

		public void close() throws IOException {
			fin.close();
		}

		// public boolean markSupport() { return false; }
		public int read() throws IOException {
			try {
				int value = fin.read();
				if (value >= 0) {
					this.position++;
				}
				return value;
			} catch (IOException e) { // unexpected exception
				throw new IOException(e); // assume native fs error
			}
		}

		public int read(byte[] b, int off, int len) throws IOException {
			try {
				int value = fin.read(b, off, len);
				if (value > 0) {
					this.position += value;
				}
				return value;
			} catch (IOException e) { // unexpected exception
				throw new IOException(e); // assume native fs error
			}
		}

		public int read(long position, byte[] b, int off, int len)
				throws IOException {
			ByteBuffer bb = ByteBuffer.wrap(b, off, len);
			try {
				return fin.getChannel().read(bb, position);
			} catch (IOException e) {
				throw new IOException(e);
			}
		}

		public long skip(long n) throws IOException {
			long value = fin.skip(n);
			if (value > 0) {
				this.position += value;
			}
			return value;
		}

	}

	public class GDFSOutputStream extends OutputStream implements Syncable {
		FileOutputStream fout;

		private GDFSOutputStream(GriDoc gridoc, Path f, boolean append)
				throws IOException {
			this.fout = new FileOutputStream(pathToFile(gridoc, f), append);
		}

		private GDFSOutputStream(Path f, boolean append) throws IOException {
			this.fout = new FileOutputStream(pathToFile(
					namesystem.getGridoc(0), f), append);

		}

		public void close() throws IOException {
			fout.close();
		}

		public void flush() throws IOException {
			fout.flush();
		}

		public void write(byte[] b, int off, int len) throws IOException {
			try {
				fout.write(b, off, len);
			} catch (IOException e) { // unexpected exception
				throw new IOException(e); // assume native fs error
			}
		}

		public void write(int b) throws IOException {
			try {
				fout.write(b);
			} catch (IOException e) { // unexpected exception
				throw new IOException(e); // assume native fs error
			}
		}

		/** {@inheritDoc} */
		public void sync() throws IOException {
			fout.getFD().sync();
		}

	}

	class LocatingParagraphFileInputStream extends FileInputStream {

		public LocatingParagraphFileInputStream(File file)
				throws FileNotFoundException {
			super(file);
		}

		public int read() throws IOException {
			int result = super.read();
			if (result != -1) {
				statistics.incrementBytesRead(1);
			}
			return result;
		}

		public int read(byte[] data) throws IOException {
			int result = super.read(data);
			if (result != -1) {
				statistics.incrementBytesRead(result);
			}
			return result;
		}

		public int read(byte[] data, int offset, int length) throws IOException {
			int result = super.read(data, offset, length);
			if (result != -1) {
				statistics.incrementBytesRead(result);
			}
			return result;
		}

	}

	public FSDataInputStream open(GriDoc gridoc, Path f, int buffersize)
			throws IOException {
		if (!exists(gridoc, f)) {
			throw new FileNotFoundException(f.toString());
		}
		return new FSDataInputStream(new BufferedFSInputStream(
				new GDFSInputStream(gridoc, f), buffersize));
	}

	/**
	 *
	 * @param gridoc
	 * @param f
	 * @param overwrite
	 * @param createParent
	 * @param bufferSize
	 * @return
	 * @throws Exception
	 */
	public FSDataOutputStream create(GriDoc gridoc, Path f, boolean overwrite,
			boolean createParent, int bufferSize) throws Exception {
		if (exists(gridoc, f) && !overwrite) {
			throw new IOException("File already exists:" + gridoc + f);
		}
		Path parent = f.getParent();
		if (parent != null) {
			if (!createParent && !exists(gridoc, parent)) {
				throw new FileNotFoundException("父节点不存在：" + parent
						+ " 必须先创建父节点！");
			} else if (createSection(gridoc, parent) == null) {
				throw new IOException("无法创建节" + parent);
			}
		}
		Paragraph prg=createParagraph(gridoc, f);
		return new FSDataOutputStream(new BufferedOutputStream(
				new GDFSOutputStream(gridoc, prg.filePath, true), bufferSize), statistics);
	}

	/**
	 *
	 *
	 * @param gridoc
	 * @param path
	 * @param bufferSize
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public FSDataOutputStream append(GriDoc gridoc, Path path, int bufferSize)
			throws IOException {
		// GriDoc gridoc = namesystem.getGridoc(0);
		if (!exists(gridoc, path)) {
			throw new FileNotFoundException("File " + path + " not found.");
		}
		if (gridoc.getNode(path.toString()).isShared) {
			throw new IOException("Cannot append to a section (=" + path
					+ " ).");
		}
		return new FSDataOutputStream(new BufferedOutputStream(
				new GDFSOutputStream(path, true), bufferSize));
	}

	public FSDataOutputStream append(Path path, int bufferSize)
			throws IOException {
		GriDoc gridoc = this.namesystem.getGridoc(0);
		return append(gridoc, path, bufferSize);
	}

	/**
	 *
	 *
	 * @param gridoc
	 * @param path
	 * @param bytes
	 * @param bufferSize
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public boolean appendData(GriDoc gridoc, Path path, byte[] bytes,
			int bufferSize, boolean overwrite, boolean isSynchronous)
			throws IOException, Exception {
		if (!overwrite) {
			if (!isSynchronous) {
				gridoc.dir.desynchronize(path.toString());
			}
			return gridoc.appendData(path.toString(), bytes);
		} else {

			create(gridoc, path, true);
			FSDataOutputStream out = append(gridoc, path, bufferSize);
			out.write(bytes);
			return true;
		}

	}

	public FSDataOutputStream create(GriDoc gridoc, Path f, boolean overwrite,
			int bufferSize) throws Exception {
		return create(gridoc, f, overwrite, true, bufferSize);
	}

	public FSDataOutputStream create(GriDoc gridoc, Path f, boolean overwrite)
			throws Exception {
		return create(gridoc, f, overwrite, 4096);
	}

	public boolean delete(GriDoc gridoc, Path path, boolean recursive,
			boolean isSynchronous) throws Exception {
		File f = pathToFile(gridoc, path);
		GriDocData gd = gridoc.getNode(path.toString());
		if (!gd.isSection()) {
			f.delete();
			gridoc.namesystem.deleted.add(gd);
			return gridoc.delete(path.toString(), isSynchronous);
		} else if ((!recursive) && gd.isSection() && gd.childList.size() != 0) {
			throw new IOException("进行的是非递归删除，但节" + gd + "不空，所以不能删除！");
		}
		return gridoc.delete(path.toString(), isSynchronous);
	}

	public File pathToFile(GriDoc gridoc, Path path) {
		// checkPath
		Path filePath = gridoc.getNode(path.toString()).filePath;
		if (!path.isAbsolute()) {
			path = new Path(new Path(this.workingDirectory, gridoc.griDocPath),
					filePath);
		}
		return new File(path.toUri().getPath());
	}

	public Section createSection(GriDoc gridoc, Path path)
			throws FileNotFoundException, Exception {
		return gridoc.createSection(path.toString());
	}
	public Section createSection(Path path) throws FileNotFoundException, Exception{
		GriDoc gridoc=this.namesystem.getGridoc(0);
		return createSection(gridoc,path);
	}

	/**
	 *
	 * @param gridoc
	 * @param path
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public Paragraph createParagraph(GriDoc gridoc, Path path, Path filePath)
			throws FileNotFoundException, Exception {
		return gridoc.createParagraph(path.toString(), filePath.toString());
	}
	/**
	 *
	 * @param gridoc
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public Paragraph createParagraph(GriDoc gridoc, Path path)
			throws FileNotFoundException, Exception {
		Path filepath = new Path(new Path(gridoc.namesystem.workingDirectory,
				gridoc.griDocPath), path);
		return createParagraph(gridoc, path, filepath);
	}

	public Paragraph createParagraph(Path path) throws FileNotFoundException, Exception{
		GriDoc gridoc=this.namesystem.getGridoc(0);
		return createParagraph(gridoc,path);
	}
	/**
	 *
	 * @param gridoc
	 * @param src包含所要复制的段名
	 * @return
	 * @throws Exception
	 */
	public Paragraph copyParagraphFrom(GriDoc gridoc, Path src) throws Exception{
		if(!gridoc.exists(src.toString())){
			throw new Exception("所指定路径不存在！");
		}
		if(gridoc.getNode(src.toString()).gridocDataType==DataType.PARAGRAPH){
			Paragraph prg= (Paragraph)gridoc.getNode(src.toString());
			return (Paragraph) prg.clone();
		}else {
			throw new Exception("所指定路径的数据类型不为段数据。");
		}

	}
	/**
	 *
	 * @param prg
	 * @param gridoc
	 * @param path 包含所要复制的段名。
	 * @return
	 * @throws Exception
	 */
	public Paragraph copyParagraphTo(Paragraph prg, GriDoc gridoc,Path path) throws Exception{
		if(gridoc.exists(path.toString())){
			throw new Exception("目标地址已存在，无法复制到该路径下，请先删除！");
		}else {
			if(!gridoc.getNode(path.getParent().toString()).isSection()){
				throw new Exception("不能向非节数据下复制数据。");
			}
			Paragraph clone=(Paragraph) prg.clone();
			clone.setName(path.getName());
			//Section parent=createSection(gridoc, path.getParent());
			//parent.addChild(clone);
			gridoc.addExistingParagraphTo(path.getParent().toString(), clone);//如果路径上的节不存在，则先创建。
			return clone;
		}

	}

	public Section copySectionFrom(GriDoc gridoc, Path src) throws Exception{
		if(!gridoc.exists(src.toString())){
			throw new Exception("所指定路径不存在！");
		}
		if(gridoc.getNode(src.toString()).gridocDataType==DataType.SECTION){
			Section sec=(Section)gridoc.getNode(src.toString());
			return (Section)sec.clone();
		}else {
			throw new Exception("所指定路径的数据类型不是节。");
		}
	}

	/**
	 *
	 * @param sec
	 * @param gridoc
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public Section copySectionTo(Section sec, GriDoc gridoc,Path path) throws Exception{
		if(gridoc.exists(path.toString())){
			throw new Exception("目标地址已存在，无法复制到该路径下，请先删除！");
		}else {
			if(!gridoc.getNode(path.getParent().toString()).isSection()){
				throw new Exception("不能向非节数据下复制数据。");
			}
			Section clone=(Section)sec.clone();
			clone.setName(path.getName());
			Section parent=createSection(gridoc, path.getParent());
			parent.addChild(clone);
			//((Section)gridoc.getNode(path.getParent().toString())).addChild(clone);
			return clone;
		}
	}


	public boolean deleteParagraph(GriDoc gridoc, Path path,boolean isSynchronous) throws Exception{
		if(gridoc.getNode(path.toString()).isSection()){
			throw new Exception("所要删除的数据不是段数据！");
		}
		return delete(gridoc,path,true,isSynchronous);
	}

	public boolean deleteSection(GriDoc gridoc, Path path, boolean recursive,boolean isSynchronous) throws Exception{
		if(gridoc.getNode(path.toString()).isSection()){
			return delete(gridoc,path,recursive,isSynchronous);
		}else {
			throw new Exception("所要删除的数据不是节数据！");
		}
	}

	/**
	 * 从path下读取所代表段数据的全部内容
	 * @param gridoc
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public byte[] readParagraph(GriDoc gridoc, Path path) throws Exception{
		if(!gridoc.getNode(path.toString()).isSection()){
			Paragraph prg=(Paragraph)gridoc.getNode(path.toString());
			return prg.readAll();
		}else {
			throw new IOException("路径\""+path+"\"下的数据非段类型，无法读取！");

		}
	}

	/**
	 *
	 * @param gridoc
	 * @param path
	 * @param position
	 * @param length
	 * @return
	 * @throws IOException
	 */
	public byte[] readParagraph(GriDoc gridoc,Path path, int position,int length) throws IOException{
		FSDataInputStream in =open(gridoc,path,4096);
		byte[] bytes=new byte[length];
		in.read(position, bytes, 0, length);
		return bytes;
	}

	public boolean writeParagraph(GriDoc gridoc,Path path, byte [] bytes,boolean isSynchronous) throws IOException, Exception{
		return appendData(gridoc, path, bytes, 4096,false, isSynchronous);
	}

	/**
	 * to包含目标段名
	 * @param srcGridoc
	 * @param from
	 * @param dstGridoc
	 * @param to
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public boolean moveSection(GriDoc srcGridoc,Path from, GriDoc dstGridoc,Path to) throws FileNotFoundException, Exception{
		if(!exists(dstGridoc,to)){
			if(srcGridoc.getNode(from.toString()).gridocDataType==DataType.SECTION){
				Section obj=(Section) srcGridoc.getNode(from.toString());
				Section parent=createSection(dstGridoc, to.getParent());//确保路径上的节都存在。
				obj.setName(to.getName());
				parent.addChild(obj);
				//dstGridoc.addExistingParagraphTo(to.toString(),obj);
				srcGridoc.delete(from.toString(), false);
				return true;
			}else {
				throw new Exception("不能移动非段类型的数据！");
			}
		}else {
			throw new Exception("目标路径下已存在数据，不能移动！");
		}
	}

	public boolean moveSection(GriDoc gridoc, Path from, Path to) throws FileNotFoundException, Exception{
		return moveSection(gridoc, from, gridoc, to);
	}

	/**
	 * to包含目标段名
	 * @param srcGridoc
	 * @param from
	 * @param dstGridoc
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public boolean moveParagraph(GriDoc srcGridoc,Path from, GriDoc dstGridoc, Path to) throws Exception{
		if(!exists(dstGridoc,to)){
			if(srcGridoc.getNode(from.toString()).gridocDataType==DataType.PARAGRAPH){
				Paragraph obj=(Paragraph) srcGridoc.getNode(from.toString());
				obj.setName(to.getName());
				//Section parent=createSection(dstGridoc, to.getParent());//确保路径上不存在未创建的节
				//parent.addChild(obj);
				dstGridoc.addExistingParagraphTo(to.getParent().toString(),obj);
				srcGridoc.delete(from.toString(), false);
				return true;
			}else {
				throw new Exception("不能移动非段类型的数据！");
			}
		}else {
			throw new Exception("目标路径下已存在数据，不能移动！");
		}
	}

	/**
	 * 默认在同一个格文档下移动。
	 * @param gridoc
	 * @param from
	 * @param to
	 * @return
	 * @throws Exception
	 */
	public boolean moveParagraph(GriDoc gridoc, Path from,Path to) throws Exception{
		return moveParagraph(gridoc, from, gridoc, to);
	}

	/**
	 *
	 * @param srcGridoc
	 * @param from
	 * @param dstGridoc
	 * @param to
	 * @param deleteSource
	 * @return
	 * @throws Exception
	 */
	public Paragraph mergeParagraph(GriDoc srcGridoc, Path from, GriDoc dstGridoc,Path to,boolean deleteSource) throws Exception{
		if(srcGridoc.getNode(from.toString()).isSection()){
			throw new Exception("源数据不为段类型，不能合并！");
		}
		if(dstGridoc.getNode(to.toString()).isSection()){
			throw new Exception("目标数据不为段类型，不能合并！");
		}

		Paragraph srcParagraph=(Paragraph) srcGridoc.getNode(from.toString());
		Paragraph dstParagraph=(Paragraph) dstGridoc.getNode(to.toString());
		if(deleteSource){
			srcGridoc.delete(from.toString(), false);
		}
		return dstGridoc.mergeParagraphs(srcParagraph, dstParagraph);
	}

	public Paragraph mergeParagraph(GriDoc srcGridoc,Path from,GriDoc dstGridoc,Path to) throws Exception{
		return mergeParagraph(srcGridoc, from, dstGridoc, to, false);
	}

	public Paragraph merParagraph(GriDoc gridoc,Path from,Path to) throws Exception{
		return mergeParagraph(gridoc, from, gridoc, to);
	}

	public ArrayList<String> locateByName(GriDoc gridoc,String name){
		return gridoc.locateByName(name);
	}
	public ArrayList<String> locateByObject(GriDoc gridoc, GriDocData grd){
		return gridoc.locateByObject(grd);
	}


	public boolean exists(GriDoc gridoc, Path path) {
		return gridoc.exists(path.toString());
	}

	public boolean rename(GriDoc gridoc, String src, String dst)
			throws Exception {
		return gridoc.renameTo(src, dst);
	}



	public int getDefaultPort(){
		return ParagraphEngine.DEFAULT_PORT;
	}
	public String toString() {
	    return "GriDocFileSystem";
	  }
























	/**
	 * Abstract methods
	 */

	/**
	 * 父类上的抽象方法实现。
	 *
	 * @param f
	 * @param overwrite
	 * @param createParent
	 * @param bufferSize
	 * @param replication
	 * @param blockSize
	 * @param progress
	 * @return
	 * @throws IOException
	 */
	public FSDataOutputStream create(Path f, boolean overwrite,
			boolean createParent, int bufferSize, short replication,
			long blockSize, Progressable progress) throws IOException {
		if (exists(f) && !overwrite) {
			throw new IOException("File already exists:" + f);
		}
		Path parent = f.getParent();
		if (parent != null) {
			if (!createParent && !exists(parent)) {
				throw new FileNotFoundException(
						"Parent directory doesn't exist: " + parent);
			} else if (!mkdirs(parent)) {
				throw new IOException("Mkdirs failed to create " + parent);
			}
		}
		return new FSDataOutputStream(new BufferedOutputStream(
				new GDFSOutputStream(f, false), bufferSize), statistics);
	}

	public FSDataOutputStream create(Path f, FsPermission permission,
			boolean overwrite, int bufferSize, short replication,
			long blockSize, Progressable progress) throws IOException {
		FSDataOutputStream out = create(f, overwrite, bufferSize, replication,
				blockSize, progress);
		// setPermission(f, permission);
		return out;
	}

	public URI getUri() {
		return uri;
	}

	public FSDataInputStream open(Path f, int bufferSize) throws IOException {
		GriDoc gridoc = namesystem.getGridoc(0);
		if (!exists(gridoc, f)) {
			throw new FileNotFoundException(f.toString());
		}
		return new FSDataInputStream(new BufferedFSInputStream(
				new GDFSInputStream(gridoc, f), bufferSize));
	}

	public FSDataOutputStream append(Path f, int bufferSize,
			Progressable progress) throws IOException {
		GriDoc gridoc = namesystem.getGridoc(0);
		if (!exists(gridoc, f)) {
			throw new FileNotFoundException("File " + f + " not found.");
		}
		if (gridoc.getNode(f.toString()).isShared) {
			throw new IOException("Cannot append to a section (=" + f + " ).");
		}
		return new FSDataOutputStream(new BufferedOutputStream(
				new GDFSOutputStream(f, true), bufferSize), statistics);
	}

	public boolean delete(Path p, boolean recursive) throws IOException {
		GriDoc gridoc = this.namesystem.getGridoc(0);
		try {
			return delete(gridoc, p, recursive, true);// 默认同步删除
		} catch (Exception e) {
			return false;
		}

	}

	public boolean delete(Path p) throws IOException {
		return delete(p, true);
	}

	/**
	 * 为习惯采用目录表示方法提供了创建节的途径。
	 */
	public boolean mkdirs(Path f) throws IOException {
		GriDoc gridoc = this.namesystem.getGridoc(0);
		try {
			if (createSection(gridoc, f) != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean mkdirs(Path f, FsPermission permission) throws IOException {
		return mkdirs(f);
	}

	public boolean rename(Path src, Path dst) throws IOException {
		GriDoc gridoc = this.namesystem.getGridoc(0);
		try {
			return rename(gridoc, src.toString(), dst.toString());
		} catch (Exception e) {

			return false;
		}
	}

	public FileStatus getFileStatus(Path f) throws IOException {
		File path = pathToFile(this.namesystem.getGridoc(0), f);
		if (path.exists()) {
			return new FileStatus(0, true, 0, 0, 0, f);
		} else {
			throw new IOException();
		}
	}

	public FileStatus[] listStatus(Path f) throws IOException {
		FileStatus[] results = null;
		return results;
	}

	public Path getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(Path newDir) {
		workingDirectory = newDir;
	}
}
