package node.type.models.conduct;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.IndexTransaction;
import dlt.client.tangle.hornet.model.transactions.Transaction;
import dlt.client.tangle.hornet.model.transactions.reputation.Evaluation;
import java.util.logging.Logger;
import node.type.models.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

/**
 * Nó do tipo honesto.
 *
 * @author Allan Capistrano
 * @version 1.0.0
 */
public class Honest extends Conduct {

  private static final Logger logger = Logger.getLogger(Honest.class.getName());

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a
   * Tangle.
   * @param id String - Identificador único do nó.
   */
  public Honest(LedgerConnector ledgerConnector, String id, String group) {
    super(ledgerConnector, id, group);
    this.defineConduct();
  }

  /**
   * Define o comportamento do nó.
   */
  @Override
  public void defineConduct() {
    this.setConductType(ConductType.HONEST);
  }

  /**
   * Avalia o serviço que foi prestado pelo dispositivo, de acordo com o tipo de
   * comportamento do nó.
   *
   * @param serviceProviderId String - Id do provedor do serviço que será
   * avaliado.
   * @param value float - Valor da avaliação.
   * @param provided boolean - Indica se o serviço foi prestado corretamente ou
   * não.
   * @throws InterruptedException
   */
  @Override
  public void evaluateServiceProvider(
    String serviceProviderId,
    float value,
    boolean provided
  ) throws InterruptedException {
    if (provided) {
      logger.info("Provided the service.");
    } else {
      logger.info("Did not provide the service.");
    }

    Transaction transactionEvaluation = new Evaluation(
      this.getId(),
      serviceProviderId,
      this.getGroup(),
      TransactionType.REP_EVALUATION,
      value
    );

    // Adicionando avaliação na Tangle.
    this.getLedgerConnector()
      .put(new IndexTransaction(serviceProviderId, transactionEvaluation));
  }
}
