package node.type.models.conduct;

import java.util.logging.Logger;
import node.type.models.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

/**
 * Nó do tipo egoísta.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class Selfish extends Conduct {

  private static final Logger logger = Logger.getLogger(
    Selfish.class.getName()
  );

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a
   * Tangle.
   * @param id String - Identificador único do nó.
   */
  public Selfish(LedgerConnector ledgerConnector, String id, String group) {
    super(ledgerConnector, id, group);
    this.defineConduct();
  }

  /**
   * Define o comportamento do nó.
   */
  @Override
  public void defineConduct() {
    this.setConductType(ConductType.SELFISH);
  }

  /**
   * Avalia o serviço que foi prestado pelo dispositivo, de acordo com o tipo de
   * comportamento do nó.
   *
   * @param serviceProviderId String - Id do provedor do serviço que será
   * avaliado.
   * @param value int - Valor da avaliação.
   * @throws InterruptedException
   */
  @Override
  public void evaluateServiceProvider(String serviceProviderId, int value)
    throws InterruptedException {
    logger.info("Selfish node does not evaluate the service provider.");
  }
}
