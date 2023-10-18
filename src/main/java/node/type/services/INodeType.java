package node.type.services;

import dlt.id.manager.services.IIDManagerService;
import node.type.models.conduct.Conduct;

public interface INodeType {
  public Conduct getNode();

  public IIDManagerService getIdManager();
}
