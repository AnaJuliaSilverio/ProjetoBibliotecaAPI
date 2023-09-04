# Sistema de Biblioteca

Este é um sistema de biblioteca que segue algumas restrições essenciais para o seu funcionamento:

## Mapeamento conceitual
![modeloconceitualbiblio.PNG](projetobibliotecaAPI%2Fmodeloconceitualbiblio.PNG)

## Restrições

### Relação Autor-Livro
- Cada livro possui um único autor, mas um autor pode ter escrito vários livros. Isso permite uma organização eficiente de todos os livros presentes na biblioteca, rastreando facilmente quem é o autor de cada obra.

### Relação Empréstimo-Livro-Leitor
- Cada empréstimo é associado a um único livro. Isso garante um controle rigoroso sobre os empréstimos, evitando duplicações e garantindo que cada leitor tenha apenas um livro emprestado por vez.

### Histórico de Empréstimos
- Um livro pode ter vários empréstimos ao longo do tempo, mas apenas um empréstimo por vez. Isso significa que, embora um livro possa ser emprestado várias vezes, ele não pode estar simultaneamente emprestado para mais de um leitor. Isso evita conflitos de disponibilidade e garante uma experiência justa para todos os leitores.

## Tecnologias Utilizadas 💻

- **Spring Boot:** O framework Spring Boot é usado para construir a aplicação Java de forma rápida e simplificada.
- **PostgreSQL:** O banco de dados PostgreSQL é utilizado para armazenar os dados dos livros.
- **Flyway:** Permite que os desenvolvedores gerenciem a evolução do banco de dados de maneira confiável, automatizada e incremental.

## Configurações ⚙️

### ModelMapper Configuration
A classe ModelMapperConfiguration configura o ModelMapper para mapear dtos para models.

### Controller
- O `AuthorController` é responsável por lidar com as requisições HTTP relacionadas aos autores.
- O `BookController` é responsável por lidar com as requisições HTTP relacionadas aos livros.
- O `LoanController` é responsável por lidar com as requisições HTTP relacionadas aos empréstimos.

### Controller Advice
A classe `ControllerExceptionHandler` é responsável por lidar com exceções personalizadas e retornar respostas HTTP apropriadas. Ela trata exceções como violações de integridade de dados, entidades não encontradas, formatos de data incorretos e nossa exceção personalizada para status dos empréstimos.

### Entidades

#### Entidade Book
A classe `Book` é uma entidade JPA que representa um livro e é mapeada para a tabela `livros` no banco de dados PostgreSQL.

#### Entidade Author
A classe `Author` é uma entidade JPA que representa um autor e é mapeada para a tabela `autores` no banco de dados PostgreSQL.

#### Entidade Empréstimo
A classe `Loan` é uma entidade JPA que representa um empréstimo e é mapeada para a tabela `empréstimos` no banco de dados PostgreSQL.

## DTOs
Os DTOs são objetos de transferência de dados utilizados para comunicação entre a camada de controle e a camada de serviço. O DTO `ExceptionResponseDTO` é usado para exceções.

## Service
São responsáveis pela lógica de negócios relacionada às entidades. Eles realizam a criação, leitura, atualização e remoção de entidades, utilizando o ModelMapper para mapear entre modelos e DTOs.

## Repository
Os repositórios são interfaces que estendem JpaRepository do Spring Data JPA, permitindo a interação com as tabelas no banco de dados. Essa interação facilita o acesso e a manipulação dos dados.