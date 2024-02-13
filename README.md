# SOFT-IoT-Node-Type

O `soft-iot-node-type` é o _bundle_ responsável pelo gerenciamento dos tipos de nós para o serviço de reputação. Existem quatro tipos de nós:
- `Honesto`: São caracterizados por um comportamento previsível e confiável;
- `Malicioso`: São caracterizados por um comportamento arbitrário. Seu comportamento varia, de acordo com uma função probabilística;
- `Perturbador`: Prestam bons serviços e avaliam corretamente os serviços solicitados, para tentar construir altas reputações. Depois passam a adotar um comportamento malicioso;
- `Egoísta`: São aqueles que não agregam no sistema de reputação.

## Configurações

| Propriedade | Descrição | Valor Padrão |
| ----------- | --------- | ------------ |
| nodeType<sup>1</sup> | Define qual é o tipo do nó. | 1 |
| honestyRate | Probabilidade (em %) do comportamento para o nó malicioso. | 50 |

###### Obs<sup>1</sup>: Opções dos tipos de nós: 1 -> Honesto, 2 -> Malicioso, 3 -> Egoista, 4 -> Corrupto, 5 -> Perturbador. ######
