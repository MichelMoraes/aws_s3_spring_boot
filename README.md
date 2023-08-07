# aws_s3_spring_boot

CRUD S3 AWS + Spring Boot + Java 17

Um breve resumo sobre Amazon s3

O Amazon Simple Storage Service é um serviço de armazenamento de objetos com escala infinita.

Alguns conceitos Amazon S3:

Buckets: São diretórios e possuem um nome único globalmente
Objects: São arquivos que possuem uma key e essa key é o caminho completo. Por exemplo: s3://my-bucket/my-file.txt
O tamanho máximo de um objeto é 5TB e caso o upload seja maior do que 5GB o multipart upload deve ser usado.

Versionamento: É ativado no nível do bucket. A mesma chave sobrescreve e incrementa a versão: 1, 2, 3… É a melhor prática pra versionar seus arquivos.
Em “Propriedades” do bucket é possível ativar isso.

S3 também pode manter sites estáticos e disponibilizá-los na internet. Caso o retorno seja um HTTP 403 (forbidden), é bom dar uma olhada na policy e ter certeza que ela permita acessos públicos.


