Instalando aws-cli e cdk

Para instalar o aws-cli, vamos baixar o arquivo contido no [link](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html), bem como seguir o passo a passo orientado.

Após instalado, devemos criar um IAM user no console do aws, cadastrando um access key e secret key. Tendo essas informações,
Devemos executar o seguinte comando:

```
aws configure
```

Forneça os dados e estará configurado.

Será necessário também instalar o cdk. Para isso, precisamos instalar, além do Java, as seguintes ferramentas:
* [node.js](https://nodejs.org/en/download/)
* [Binário do Maven](https://maven.apache.org/download.cgi)

Com relação ao binário do Maven, precisamos, após descompactar o conteúdo em um diretório, salvar esse caminho numa 
variável de sistema chamada MAVEN_HOME. E também inserir na variável path %MAVEN_HOME%\bin.

Feito esses passos, executar:

```
 npm install -g aws-cdk
```

Após isso, executar o comando abaixo:
```
cdk bootstrap
```

## Criando VPC

Podemos listar as VPCs existentes com o comando abaixo:

```
cdk list
```

No nosso caso, apresentou **Vpc**, que era a VPC cadastrada na aplciação no projeto Java

```
cdk init app --language java
```

Então, para fazer o deploy, executamos:
```
cdk deploy Vpc
```

### Criação de Clusters

```
public class ClusterStack extends Stack {
    public ClusterStack(final Construct scope, final String id, Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public ClusterStack(final Construct scope, final String id, final StackProps props, Vpc vpc) {
        super(scope, id, props);

        Cluster.Builder.create(this, id)
                .clusterName("cluster-01")
                .vpc(vpc)
                .build();
    }
}
```
Podemos observar que a criação do cluster tem dependência com a Vpc. Dessa forma, podemos executar o comando abaixo 
para ver as diferenças desde o último deploy:
```
cdk list
```

Na nossa classe principal, ficará assim:
```
public class CursoAwsCdkApp {
    public static void main(final String[] args) {
        App app = new App();

        VpcStack vpcStack = new VpcStack(app, "Vpc");
        ClusterStack clusterStack = new ClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);
        app.synth();
    }
}
```
Nota-se que o cluster tem uma depenência com a Vpc.