package node.type.models.conduct;

import node.type.models.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

/**
 * Comportamento base para os tipos de nó.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public abstract class Conduct {

  private ConductType conductType;
  private final LedgerConnector ledgerConnector;
  private final String id;
  private final String group;

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a
   * Tangle.
   * @param id String - Identificador único do nó.
   */
  public Conduct(LedgerConnector ledgerConnector, String id, String group) {
    this.ledgerConnector = ledgerConnector;
    this.id = id;
    this.group = group;
  }

  /**
   * Define o comportamento do nó.
   */
  public abstract void defineConduct();

  /**
   * Avalia o serviço que foi prestado, de acordo com o tipo de
   * comportamento do nó.
   *
   * @param serviceProviderId String - Id do dispositivo que será avaliado.
   * @param value int - Valor da avaliação.
   * @throws InterruptedException
   */
  public abstract void evaluateServiceProvider(
    String serviceProviderId,
    int value
  ) throws InterruptedException;

  public ConductType getConductType() {
    return conductType;
  }

  public void setConductType(ConductType conductType) {
    this.conductType = conductType;
  }

  public LedgerConnector getLedgerConnector() {
    return ledgerConnector;
  }

  public String getId() {
    return id;
  }

  public String getGroup() {
    return group;
  }
}
