# 🥩 Brasa's Churrascaria — Assistente Virtual de IA

> Assistente virtual inteligente para atendimento e geração de orçamentos de eventos de churrasco, desenvolvido com **Spring Boot** e **LangChain4j**.

---

## 📌 Sobre o Projeto

O **AssistantAi** é o assistente virtual da **Brasa's Churrascaria**. Ele conduz o cliente por todo o processo de atendimento — do primeiro contato até a apresentação de um orçamento detalhado — de forma conversacional, guiada e totalmente automatizada.

O principal objetivo técnico do projeto foi explorar na prática as funcionalidades de **`@AiService`** e **`@Tool`** do [LangChain4j](https://docs.langchain4j.dev/), integrando um LLM com ferramentas reais de negócio de forma declarativa e eficiente.

---

## 🧠 Destaques Técnicos — `@AiService` e `@Tool`

### `@AiService`

O `@AiService` é uma das funcionalidades mais poderosas do LangChain4j. Com ele, é possível criar um agente de IA completo apenas declarando uma **interface Java** — sem implementação manual:

```java
@AiService
public interface AiAssistantService extends ChatMemoryAccess {

    @SystemMessage("""
        Você é o assistente virtual da Brasa's Churrascaria...
        [fluxo de atendimento, regras de negócio, tom de voz]
    """)
    String message(@MemoryId String conversationId,
                   @UserMessage String message);
}
```

O LangChain4j injeta automaticamente:
- O modelo de linguagem configurado (`ChatModel`)
- O provedor de memória (`ChatMemoryProvider`)
- As ferramentas (`@Tool`) disponíveis no contexto Spring

### `@Tool`

As ferramentas são **métodos Java anotados com `@Tool`** que o LLM pode invocar autonomamente quando precisar de informações ou cálculos reais. O modelo decide, com base na descrição, quando e como usar cada ferramenta:

```java
@Tool("""
    Calcula o custo do churrasco para o evento.
    Use esta ferramenta quando o cliente informar: tipo de churrasco,
    quantidade de adultos, crianças e duração do evento.
""")
public BigDecimal budgetBBQ(BBQ bbqType, int adults, int childrenUnder12, int eventDurationHours) {
    return service.calculateBBQ(bbqType, adults, childrenUnder12, eventDurationHours);
}
```

**Ferramentas implementadas:**

| Tool | Descrição |
|------|-----------|
| `budgetBBQ` | Calcula o custo do churrasco (Premium ou Essencial) por tipo, convidados e duração |
| `budgetBeer` | Calcula o custo de cervejas (Brahma, Heineken, Skol 600ml) |
| `budgetJuice` | Calcula o custo de sucos naturais (Laranja, Abacaxi, Maracujá 1L) |
| `sumTotalBudget` | Soma todos os valores e retorna o total final do orçamento |
| `menuDrinks` | Retorna o cardápio de bebidas disponíveis |
| `bbqMenu` | Retorna o cardápio completo (Premium e Essencial) da churrascaria |

---

## ✨ Funcionalidades

- 🤝 **Atendimento conversacional guiado** — Uma pergunta por vez, sem sobrecarregar o cliente
- 🗂️ **Qualificação de evento** — Coleta tipo de evento, número de adultos, crianças e duração
- 🥩 **Cardápio interativo** — Apresenta os menus Premium e Essencial sob demanda
- 🍺 **Orçamento de bebidas** — Cervejas (Brahma, Heineken, Skol 600ml) e Sucos (Laranja, Abacaxi, Maracujá 1L)
- 💰 **Cálculo automático de orçamento** — Preços consultados em banco de dados, com regras de negócio aplicadas
- 🧠 **Memória de conversa por sessão** — Janela de 30 mensagens via `ChatMemoryProvider`
- 🆔 **Sessões independentes** — Cada conversa possui um `conversationId` único (UUID)
- 🌐 **Interface Web** — Página interativa em `index.html` para conversar com o assistente em tempo real

---

## 🍽️ Cardápios

### Churrasco Premium
| Categoria | Itens |
|-----------|-------|
| Bovinos Nobres | Picanha Angus, Ancho, Chorizo |
| Suínos | Costela suína |
| Aves | Medalhão de frango com bacon |
| Entradas | Queijo coalho, Pão de alho especial |
| Acompanhamentos | Arroz branco, Arroz biro-biro, Farofa especial, Salada verde |

### Churrasco Essencial
| Categoria | Itens |
|-----------|-------|
| Bovinos | Picanha, Contra-filé |
| Suínos | Linguiça toscana |
| Aves | Coração de frango, Coxa e sobrecoxa |
| Acompanhamentos | Arroz branco, Farofa, Vinagrete, Maionese, Pão de alho |

---

## 💲 Regras de Negócio

| Regra | Detalhe |
|-------|---------|
| Preço base — Essencial | R$ 59,90 / adulto |
| Preço base — Premium | R$ 79,90 / adulto |
| Crianças (< 12 anos) | 50% do valor do adulto |
| Hora extra (> 4h) | +10% por hora extra sobre o custo do churrasco |
| Custo operacional | +15% sobre o custo do churrasco |
| Cerveja (sem qtd informada) | 1,5L por adulto, distribuído entre as marcas escolhidas |
| Suco (sem qtd informada) | 600ml por adulto / 400ml por criança |

---

## 🏗️ Arquitetura

```
src/main/java/com/gstech/AssistantAi/
├── AssistantAiApplication.java         # Ponto de entrada Spring Boot
│
├── configLLM/
│   ├── ConfigChatModel.java            # @Bean: ChatModel, ChatMemoryProvider, ChatMemoryStore
│   └── interfaces/
│       └── AiAssistantService.java     # @AiService com @SystemMessage — agente principal
│
├── controllers/
│   └── LLModelController.java          # Endpoint REST POST /api/chat
│
├── dto/
│   ├── RequestDTO.java                 # { id, UserMessage }
│   └── ResponseLLMDTO.java             # { response, conversationId }
│
├── model/
│   ├── CategoryDrink.java
│   ├── Drink.java
│   ├── Meat.java
│   └── enums/
│       ├── BBQ.java                    # CHURRASCO_PREMIUM | CHURRASCO_ESSENCIAL
│       ├── MeatType.java
│       └── NameDrink.java              # BRAHMA | HEINEKEN | SKOL | LARANJA | MARACUJA | ABACAXI
│
├── repositories/
│   └── DrinkRepository.java            # Consulta de preços das bebidas
│
└── service/
    ├── BudgetToolService.java      # @Tool — ferramentas expostas ao LLM
    ├── BudgetCalculationService.java   # Lógica de cálculo de cada item do orçamento
    ├── MenuToolsService.java           # @Tool — cardápios disponíveis para o assistente
    └── utils/
        └── BudgetCalculator.java       # Cálculos detalhados (preços, horas extras, sugestões)
```

---

## 🔌 API REST

### `POST /api/chat`

**Request:**
```json
{
  "id": "uuid-da-sessao-ou-vazio",
  "UserMessage": "Olá, gostaria de um orçamento para 30 pessoas"
}
```

> Se `id` for `null` ou vazio, um novo UUID de sessão é gerado automaticamente.

**Response:**
```json
{
  "response": "Olá! Seja bem-vindo à Brasa's Churrascaria! 🔥 ...",
  "conversationId": "3fa85f64-5717-4562-b3fc-2c963f66afa6"
}
```

---

## ⚙️ Fluxo de Atendimento

O assistente segue obrigatoriamente 5 etapas definidas no `@SystemMessage`:

```
1. ACOLHIMENTO        → Cumprimenta e identifica a necessidade do cliente
2. QUALIFICAÇÃO       → Coleta: tipo de evento, adultos, crianças, duração, data
3. ESCOLHA DO MENU    → Apresenta Premium ou Essencial conforme interesse
4. BEBIDAS            → Oferece cervejas e sucos; aceita qtd. ou usa sugestão automática
5. ORÇAMENTO FINAL    → Calcula via @Tool e apresenta o resumo formatado com totais
```

---

## 🤖 Configuração do Modelo

O projeto suporta dois provedores:

| Provedor | Status | Observação |
|----------|--------|------------|
| **Ollama** (qwen2.5:7b) | ✅ Ativo | Via API compatível com OpenAI — `http://localhost:11434/v1` |
| **Google Gemini** (gemini-2.5-flash) | 💤 Comentado | Descomente o bean em `ConfigChatModel.java` para ativar |

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.5.11 | Framework backend |
| Spring Data JPA | — | Persistência de dados |
| H2 Database | — | Banco de dados em memória com seed via `data.sql` |
| **LangChain4j Spring Boot Starter** | **1.0.0-beta19** | **`@AiService`, `@Tool`, `ChatMemoryProvider`** |
| LangChain4j Google Gemini | 1.11.0 | Suporte ao modelo Gemini |
| LangChain4j OpenAI | 1.11.0 | Compatível com Ollama |
| Lombok | — | Redução de boilerplate |

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21+
- Maven 3.8+
- [Ollama](https://ollama.com/) instalado localmente com o modelo `qwen2.5:7b` (ou outro de sua preferência)

### 1. Clone o repositório

```bash
git clone https://github.com/GuilhermeSaar/AiAssistant.git
cd AiAssistant
```

### 2. Configure as propriedades

Edite `src/main/resources/application-dev.properties`:

```properties
ollama-ai.chat-model.base.url=http://localhost:11434/v1
ollama-ai.chat-model.model-name=qwen2.5:7b
```

### 3. Suba o Ollama com o modelo

```bash
ollama run qwen2.5:7b
```

### 4. Execute a aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`
A interface de chat pode ser acessada em: `http://localhost:8080/index.html`

> O banco H2 é populado automaticamente com preços de bebidas e carnes via `data.sql` na inicialização.

---

## 📝 Licença

Este projeto é de uso proprietário da **Brasa's Churrascaria**. Todos os direitos reservados.
