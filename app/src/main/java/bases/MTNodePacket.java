package bases;

import java.util.ArrayList;

public class MTNodePacket{
	
	
	protected String _name;
	protected String _txtValue;
	protected ArrayList<MTNodePacket> _nodePacketLst;		
	
	
	public void setName(String $name){
		_name = $name;
	}	
	
	public String getName(){
		return _name;
	}
	
	public void setTextValue(String $txt){
		_txtValue = $txt;
	}
	
	public String getTextValue(){
		return _txtValue;
	}	
	
	
	
	public void putNodePacketToList(MTNodePacket $packet){
		
		if(_nodePacketLst==null){
			_nodePacketLst = new ArrayList<MTNodePacket>();
		}
		
		_nodePacketLst.add($packet);
	}
	
	
	public MTNodePacket getNodePacketWithName(String $name){
		if(_nodePacketLst==null) return null;
		
		int kLen = _nodePacketLst.size();
		for(int i = 0;i<kLen;i++){
			MTNodePacket kPacket = _nodePacketLst.get(i);
			if($name.equalsIgnoreCase(kPacket.getName())){
				return kPacket;
			}
		}
		return null;
	}
	
	public ArrayList<MTNodePacket> getNodePacketList(){
		return _nodePacketLst;
	}
	
	public ArrayList<MTNodePacket> getNodePacketList(String $name){
		
		if(_nodePacketLst==null) return null;
		
		ArrayList<MTNodePacket> kPacketLst = new ArrayList<MTNodePacket>();
		int kLen = _nodePacketLst.size();
		for(int i = 0;i<kLen;i++){
			MTNodePacket kNodePacket = _nodePacketLst.get(i);
			if($name.equalsIgnoreCase(kNodePacket.getName())){
				kPacketLst.add(kNodePacket);
			}
		}
		return kPacketLst;
	}
}
