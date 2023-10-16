Este projeto foi desenvolvido em conjunto ao bootcamp de Java da Rocketseat.

Utilizei como base as implementações das aulas, mas segui meu caminho, implementando à minha maneira as features apresentadas no curso.

Este projeto se trata de uma simples API de To-Do list desenvolvida com SpringBoot.
Aqui, você poderá criar uma conta informando nome, usuário e senha e com as credenciais desta conta, criar uma série de tarefas.

Você também poderá listar todas as tarefas criadas nesta conta, bem como editar as informações de tarefas criadas.

Para executar, você pode utilizar do Docker rodando o comando <br>
`docker build - < Dockerfile` <br>

Após isso, rodar o comando <br>
`docker image ls` <br>

Copiar o ID do container recém gerado e subi-lo com <br>
`docker run <id_imagem>` <br>

Substituindo o id_imagem pelo ID caputrado no passo anterior. <br>

Para os próximos passos desta API, considero: <br>
[] Implementar testes unitários e de integrações; <br>
[] Implementar Swagger para documentação da API; <br>
[] Integrar com postgresql; <br>
[] Adicionar o Keycloak como gerenciador de sessões e autorizações; <br>
