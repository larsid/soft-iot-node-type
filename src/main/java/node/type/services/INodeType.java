package node.type.services;

import node.type.models.conduct.Conduct;
import node.type.models.enums.ConductType;

public interface INodeType {
  public Conduct getNode();

  public String getNodeId();
  
  public String getNodeIp();

  public String getNodeGroup();

  public ConductType getType();
}
