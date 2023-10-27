package node.type.services;

import node.type.models.conduct.Conduct;

public interface INodeType {
  public Conduct getNode();

  public String getNodeId();
  
  public String getNodeIp();

  public String getNodeGroup();
}
