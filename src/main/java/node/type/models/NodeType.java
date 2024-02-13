package node.type.models;

import dlt.id.manager.services.IDLTGroupManager;
import dlt.id.manager.services.IIDManagerService;
import java.util.logging.Logger;
import node.type.models.conduct.Conduct;
import node.type.models.conduct.Disturbing;
import node.type.models.conduct.Honest;
import node.type.models.conduct.Malicious;
import node.type.models.conduct.Selfish;
import node.type.models.tangle.LedgerConnector;
import node.type.services.INodeType;

/**
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class NodeType implements INodeType {

  private Conduct node;
  private LedgerConnector ledgerConnector;
  private IIDManagerService idManager;
  private IDLTGroupManager group;
  private int nodeType;
  private float honestyRate;

  private static final Logger logger = Logger.getLogger(
    NodeType.class.getName()
  );

  public NodeType() {}

  /**
   * Executa o que foi definido na função quando o bundle for inicializado.
   */
  public void start() {
    switch (nodeType) {
      case 1:
        this.node =
          new Honest(
            this.ledgerConnector,
            this.idManager.getID(),
            this.group.getGroup()
          );
        logger.info("Initializing a Honest Node.");
        break;
      case 2:
        this.node =
          new Malicious(
            this.ledgerConnector,
            this.idManager.getID(),
            this.group.getGroup(),
            this.honestyRate
          );
        logger.info("Initializing a Malicious Node.");
        logger.info(
          "Malicious node behavior: " + node.getConductType().toString()
        );
        break;
      case 3:
        this.node =
          new Selfish(
            ledgerConnector,
            this.idManager.getID(),
            this.group.getGroup()
          );
        logger.info("Initializing a Selfish Node.");
        break;
      case 5:
        this.node =
          new Disturbing(
            ledgerConnector,
            this.idManager.getID(),
            this.group.getGroup()
          );
        logger.info("Initializing a Disturbing Node.");
        break;
      default:
        logger.severe("Error. No node type for this option.");
        this.stop();
        break;
    }
    logger.info("Node ID: " + this.getNodeId());
  }

  /**
   * Executa o que foi definido na função quando o bundle for finalizado.
   */
  public void stop() {
    logger.info("Finishing the Node.");
  }

  @Override
  public Conduct getNode() {
    return node;
  }

  @Override
  public String getNodeId() {
    return this.idManager.getID();
  }

  @Override
  public String getNodeIp() {
    return this.idManager.getIP();
  }

  @Override
  public String getNodeGroup() {
    return this.group.getGroup();
  }

  public IIDManagerService getIdManager() {
    return idManager;
  }

  public void setNode(Conduct node) {
    this.node = node;
  }

  public LedgerConnector getLedgerConnector() {
    return ledgerConnector;
  }

  public void setLedgerConnector(LedgerConnector ledgerConnector) {
    this.ledgerConnector = ledgerConnector;
  }

  public void setIdManager(IIDManagerService idManager) {
    this.idManager = idManager;
  }

  public IDLTGroupManager getGroup() {
    return group;
  }

  public void setGroup(IDLTGroupManager group) {
    this.group = group;
  }

  public int getNodeType() {
    return nodeType;
  }

  public void setNodeType(int nodeType) {
    this.nodeType = nodeType;
  }

  public float getHonestyRate() {
    return honestyRate;
  }

  public void setHonestyRate(float honestyRate) {
    this.honestyRate = honestyRate;
  }
}
