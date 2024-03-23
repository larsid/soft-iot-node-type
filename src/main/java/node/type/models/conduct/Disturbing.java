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
 * Nó do tipo perturbador.
 *
 * @author Allan Capistrano
 * @version 1.2.0
 */
public class Disturbing extends Conduct {

  private final float honestyRate;

  private static final Logger logger = Logger.getLogger(
    Disturbing.class.getName()
  );

  /**
   * Método construtor.
   *
   * @param ledgerConnector LedgerConnector - Conector para comunicação com a
   * Tangle.
   * @param id String - Identificador único do nó.
   */
  public Disturbing(
    LedgerConnector ledgerConnector,
    String id,
    String group,
    float honestyRate
  ) {
    super(ledgerConnector, id, group);
    this.setConductType(ConductType.HONEST);
    this.honestyRate = honestyRate;
  }

  /**
   * Define o comportamento do nó perturbador.
   */
  @Override
  public void defineConduct() {
    this.changeConduct();
  }

  /**
   * Altera a conduta do nó:
   * Se ele for honesto -> muda para malicioso;
   * Se ele for malicioso -> muda para honesto.
   */
  private void changeConduct() {
    this.setConductType(
        this.getConductType() == ConductType.HONEST
          ? ConductType.MALICIOUS
          : ConductType.HONEST
      );

    logger.info(
      "Changing Disturbing node behavior to: " +
      this.getConductType().toString()
    );
  }

  /**
   * Avalia o serviço que foi prestado, de acordo com o tipo de comportamento
   * do nó.
   *
   * @param serviceProviderId String - Id do provedor do serviço que será
   * avaliado.
   * @param serviceEvaluation int - Avaliação do serviço, (0 -> não prestado
   * corretamente; 1 -> prestado corretamente).
   * @param nodeCredibility float - Credibilidade do nó avaliador.
   * @param value float - Valor da avaliação. Se o tipo de conduta for
   * 'MALICIOUS' este parâmetro é ignorado.
   * @throws InterruptedException
   */
  @Override
  public void evaluateServiceProvider(
    String serviceProviderId,
    int serviceEvaluation,
    float nodeCredibility,
    float value
  ) throws InterruptedException {
    switch (this.getConductType()) {
      case HONEST:
        switch (serviceEvaluation) {
          case 0:
            logger.info(
              "[" + serviceProviderId + "] Did not provide the service."
            );
            break;
          case 1:
            logger.info("[" + serviceProviderId + "] Provided the service.");
            break;
          default:
            logger.warning("Unable to evaluate the device");
            break;
        }
        break;
      case MALICIOUS:
        /* Gerando um número aleatório entre 0 e 100. */
        float randomNumber = new Random().nextFloat() * 100;

        if (randomNumber > this.honestyRate) {
          logger.info("[" + serviceProviderId + "] Did not provide the service.");
          /* Alterando o valor da avaliação para 'serviço não prestado'. */
          serviceEvaluation = 0;
          value = 0;
        } else {
          switch (serviceEvaluation) {
            case 0:
              logger.info(
                "[" + serviceProviderId + "] Did not provide the service."
              );
              break;
            case 1:
              logger.info("[" + serviceProviderId + "] Provided the service.");
              break;
            default:
              logger.warning("Unable to evaluate the device");
              break;
          }
        }
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
