package node.type.models.conduct;

import node.type.models.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

/**
 * Comportamento base para os tipos de nó.
 *
 * @author Allan Capistrano
 * @version 1.1.0
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
   * @param serviceEvaluation int - Avaliação do serviço, (0 -> não prestado 
   * corretamente; 1 -> prestado corretamente).
   * @param nodeCredibility float - Credibilidade do nó avaliador.
   * @param value float - Valor da avaliação.
   * @param provided boolean - Indica se o serviço foi prestado corretamente ou
   * não.
   * @throws InterruptedException
   */
  public abstract void evaluateServiceProvider(
    String serviceProviderId,
    int serviceEvaluation,
    float nodeCredibility,
    float value,
    boolean provided
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
