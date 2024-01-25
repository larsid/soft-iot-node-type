package node.type.models.conduct;

import dlt.client.tangle.hornet.enums.TransactionType;
import dlt.client.tangle.hornet.model.transactions.IndexTransaction;
import dlt.client.tangle.hornet.model.transactions.Transaction;
import dlt.client.tangle.hornet.model.transactions.reputation.Evaluation;
import java.util.Random;
import java.util.logging.Logger;
import node.type.models.enums.ConductType;
import node.type.models.tangle.LedgerConnector;

/**
 * Nó tipo malicioso.
 *
 * @author Allan Capistrano
 * @version 1.1.0
 */
public class Malicious extends Conduct {

  private final float honestyRate;
  private static final Logger logger = Logger.getLogger(
    Malicious.class.getName()
  );

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a
   * Tangle.
   * @param id String - Identificador único do nó.
   * @param honestyRate float - Taxa de honestidade do nó malicioso.
   */
  public Malicious(
    LedgerConnector ledgerConnector,
    String id,
    String group,
    float honestyRate
  ) {
    super(ledgerConnector, id, group);
    this.honestyRate = honestyRate;
    this.defineConduct(); // TODO: Criar task para alterar o comportamento desse tipo de nó de tempos em tempos.
  }

  /**
   * Define se o comportamento do nó malicioso será honesto ou desonesto.
   */
  @Override
  public void defineConduct() {
    /* Gerando um número aleatório entre 0 e 100. */
    float randomNumber = new Random().nextFloat() * 100;

    if (randomNumber > honestyRate) {
      this.setConductType(ConductType.MALICIOUS);
    } else {
      this.setConductType(ConductType.HONEST);
    }
  }

  /**
   * Avalia o serviço que foi prestado pelo dispositivo, de acordo com o tipo de
   * comportamento do nó.
   *
   * @param serviceProviderId String - Id do provedor do serviço que será
   * avaliado.
   * @param serviceEvaluation int - Avaliação do serviço, (0 -> não prestado
   * corretamente; 1 -> prestado corretamente).
   * @param nodeCredibility float - Credibilidade do nó avaliador.
   * @param value float - Valor da avaliação. Se o tipo de conduta for 
   * 'MALICIOUS' este parâmetro é ignorado.
   * @param provided boolean - Indica se o serviço foi prestado corretamente ou
   * não.
   * @throws InterruptedException
   */
  @Override
  public void evaluateServiceProvider(
    String serviceProviderId,
    int serviceEvaluation,
    float nodeCredibility,
    float value,
    boolean provided
  ) throws InterruptedException {
    switch (this.getConductType()) {
      case HONEST:
        if (provided) {
          logger.info("Provided the service.");
        } else {
          logger.info("Did not provide the service.");
        }
        break;
      case MALICIOUS:
        logger.info("Did not provide the service.");
        /* Alterando o valor da avaliação para 'serviço não prestado'. */
        value = 0;
        break;
      default:
        logger.severe("Error! ConductType not found.");
        break;
    }

    Transaction transactionEvaluation = new Evaluation(
      this.getId(),
      serviceProviderId,
      this.getGroup(),
      TransactionType.REP_EVALUATION,
      serviceEvaluation,
      nodeCredibility,
      value
    );

    /* Adicionando avaliação na Tangle. */
    this.getLedgerConnector()
      .put(new IndexTransaction(serviceProviderId, transactionEvaluation));
  }

  public float getHonestyRate() {
    return honestyRate;
  }
}
