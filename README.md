# Gerenciador de Dispositivos - Automação Residencial e Industrial

Este projeto consiste em um sistema de gerenciamento de dispositivos de automação (Sensores e Atuadores), desenvolvido como requisito para a avaliação de Programação Orientada a Objetos (POO). A aplicação possui uma interface gráfica interativa (Java Swing) integrada a um banco de dados local (SQLite) para garantir a persistência das informações.

## 🚀 Funcionalidades (CRUD Completo)
- **Cadastrar:** Adiciona novos dispositivos separando as especificidades de Sensores e Atuadores.
- **Listar:** Exibe todos os dispositivos salvos em uma tabela estruturada (`JTable`) atualizada em tempo real.
- **Editar:** Permite selecionar um dispositivo na tabela, carregar seus dados de volta ao formulário e salvar as alterações.
- **Excluir:** Remove o dispositivo selecionado permanentemente do banco de dados após confirmação.
- **⚡ Executar Teste (Polimorfismo):** Dispara uma simulação de funcionamento baseada no tipo real do objeto selecionado.

## 🧠 Conceitos de POO Aplicados
O sistema foi modelado aplicando rigidamente os pilares da programação orientada a objetos:
1. **Herança:** A classe abstrata `Dispositivo` serve como base para as classes filhas `Sensor` e `Atuador`, herdando atributos comuns como `nome`, `ipAddress` e `status`.
2. **Polimorfismo:** Método abstrato `executarTeste()` implementado nas classes filhas de forma especializada. A interface gráfica dispara o teste de maneira genérica sem precisar identificar explicitamente a instância.
3. **Encapsulamento:** Todos os atributos possuem visibilidade privada (`private`) e são acessados estritamente por métodos modificadores e de acesso (`getters` e `setters`).
4. **Collections:** Uso da API de coleções do Java (`List` e `ArrayList`) para o trânsito dinâmico de dados entre o banco de dados e a camada visual.

## 💾 Persistência de Dados (Banco de Dados)
- **Mapeamento:** Utilização da estratégia **Single Table (Tabela Única)**, onde todos os objetos da hierarquia de herança são salvos em uma única tabela (`dispositivos`), otimizando consultas locais.
- **Banco de Dados:** SQLite Local. O arquivo `automacao.db` é gerado e inicializado automaticamente na raiz do projeto logo na primeira execução.

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java (JDK 17 ou superior)
- **Interface Gráfica:** Java Swing (Construído puramente via código, sem o uso de geradores automáticos)
- **Driver de Conexão:** `sqlite-jdbc` (v3.45.1.0 ou superior)
- **Biblioteca de Log:** `slf4j-simple` (v1.7.36)

## 📦 Como Executar o Projeto

1. Certifique-se de ter o **JDK 17+** instalado em sua máquina.
2. Certifique-se de que as bibliotecas `.jar` do **SQLite JDBC** e **SLF4J** estão adicionadas ao *Classpath* / Dependências do Módulo do seu projeto na IDE.
3. Execute o projeto a partir da classe principal:
   ```text
   src/main/Principal.java