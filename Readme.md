# Gerenciador de Alunos - Android App

## Descrição

O **Gerenciador de Alunos** é um aplicativo Android desenvolvido para gerenciar informações de alunos, incluindo cadastro, edição, listagem e exclusão de registros. O aplicativo permite a captura de fotos dos alunos e armazena todas as informações localmente usando SQLite.

## Funcionalidades

- **Cadastro de Alunos**: Adicione novos alunos com informações como nome, CPF, telefone e foto.
- **Edição de Alunos**: Atualize as informações dos alunos já cadastrados.
- **Listagem de Alunos**: Visualize todos os alunos cadastrados em uma lista.
- **Exclusão de Alunos**: Remova alunos da base de dados.
- **Captura de Fotos**: Tire fotos dos alunos diretamente do aplicativo.

## Requisitos do Sistema

- Android 5.0 (Lollipop) ou superior.

## Configuração do Projeto

### Permissões Necessárias

- `android.permission.CAMERA`: Para captura de fotos.
- `android.permission.WRITE_EXTERNAL_STORAGE`: Para armazenamento de fotos.

### Estrutura do Projeto

- **`AndroidManifest.xml`**: Define as permissões e a estrutura básica do aplicativo.
- **`activity_cadastrar_aluno.xml`**: Layout para cadastro de novos alunos.
- **`activity_editar_aluno.xml`**: Layout para edição de informações de alunos.
- **`activity_listar_alunos.xml`**: Layout para listagem de alunos.
- **`activity_main.xml`**: Layout principal com opções de cadastro e listagem.
- **`Aluno.java`**: Classe modelo para representar um aluno.
- **`AlunoDao.java`**: Classe de acesso a dados para manipulação do banco de dados SQLite.
- **`CadastrarAlunoActivity.java`**: Activity para cadastro de novos alunos.
- **`Conexao.java`**: Classe para gerenciamento da conexão com o banco de dados SQLite.
- **`EditarAlunoActivity.java`**: Activity para edição de informações de alunos.
- **`ListarAlunosActivity.java`**: Activity para listagem de alunos.
- **`MainActivity.java`**: Activity principal com opções de cadastro e listagem.

## Instalação

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/gerenciador-de-alunos.git
   ```
2. Abra o projeto no Android Studio.
3. Compile e execute o aplicativo em um emulador ou dispositivo físico.

## Uso

1. **Cadastro de Aluno**:
   - Acesse a opção "Cadastrar Aluno" na tela principal.
   - Preencha as informações do aluno e tire uma foto.
   - Clique em "Cadastrar Aluno" para salvar os dados.

2. **Listagem de Alunos**:
   - Acesse a opção "Listar Alunos" na tela principal.
   - Visualize a lista de alunos cadastrados.
   - Toque em um aluno para editar ou excluir suas informações.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.