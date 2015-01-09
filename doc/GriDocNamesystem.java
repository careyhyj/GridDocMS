/**
 * 
 */
package com.csasc.gdmodel.dataoperation;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.HostsFileReader;

import com.csasc.gdmodel.GridocSystem.GriDocFileSystem;
import com.csasc.gdmodel.datamodel.GriDocData;

/**
 * @author wxyyuppie
 *代表一个PE上所有的格文档文件GriDoc，负责管理多个GriDoc。
 */
public class GriDocNamesystem {
	public Path workingDirectory;
	public static GriDocNamesystem namesystemObject;
	ArrayList<GriDoc> gridocs=new ArrayList<GriDoc>();
	ArrayList<GriDoc> openningGridocs=new ArrayList<GriDoc>();
	public ArrayList<GriDocData> deleted=new ArrayList<GriDocData>();//记录被删除的节点，定期扫描，如果没有引用，则彻底删除
	private volatile boolean fsRunning=true;
	long startTime=0;
	private boolean allowAppend=true;
	
	public InetSocketAddress paragraphEngineAddress=null;
	
	private String paragraphEngineHostName;
	
	public GriDocNamesystem(GriDocFileSystem gdFileSystem,String workingDirectory){
		this.workingDirectory=new Path(workingDirectory);
		namesystemObject=this;
	}
	
	public GriDoc registerGridoc(GriDoc gridoc) throws Exception{
		if(this.gridocs.contains(gridoc)){
			throw new Exception("the gridoc already exists, do not need registering again!");
		}
		this.gridocs.add(gridoc);
		return gridoc;
	}
	
	public boolean isGridocAvailable(GriDoc gridoc){
		if(!this.gridocs.contains(gridoc)){
			return false;
		}
		if (!gridoc.isOpenning){
			return false;
		}
		
		return true;
	}
	
	public void setGridocClosed(GriDoc gridoc){
		gridoc.isOpenning=false;
		//gridoc.close();
	}
	
	public void removeGridoc(GriDoc gridoc){
		synchronized(this){
			setGridocClosed(gridoc);
			this.gridocs.remove(gridoc);
		}
	}
	
	public GriDoc addGridoc(String path){
		return new GriDoc(path);
	}
	
	public GriDoc getGridoc(String name){
		for(GriDoc gridoc:gridocs){
			if(gridoc.name.equals(name)){
				return gridoc;
			}
		}
		return null;
	}
	
	public GriDoc getGridoc(int index){
		return gridocs.get(index);
	}
	
	public static GriDocNamesystem getNamesystem(){
		return  namesystemObject;
	}
	
	
	public Path getWorkingDirectory() {
		return workingDirectory;
	}

	
	public void setWorkspaceDirectory(Path workspaceDirectory) {
		this.workingDirectory = workspaceDirectory;
	}
}
