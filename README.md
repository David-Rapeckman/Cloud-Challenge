# Projeto SysTrack - 1º Semestre - 2TDS

Passo a passo para provisionar a infraestrutura em nuvem utilizando Azure CLI, implantar uma aplicação Java com Docker, e expor o serviço ao público utilizando uma máquina virtual no Azure.

---

## Índice

1. Criação do Grupo de Recursos  
2. Criação da Máquina Virtual  
3. Criação das Regras NSG (Segurança)  
4. Instalação do Docker na VM  
5. Dockerfile da Aplicação  
6. Build da Imagem Docker  
7. Publicação no Docker Hub  
8. Execução da Imagem na VM  
9. Acesso à Aplicação  

---

## 1. Criação do Grupo de Recursos

```bash
az group create -l brazilsouth -n rg-vm-challenge
```

---

## 2. Criação da Máquina Virtual

```bash
az vm create \
  --resource-group rg-vm-challenge \
  --name vm-challenge \
  --image Ubuntu2404 \
  --size Standard_B2s \
  --admin-username admin_fiap \
  --admin-password Admin_Fiap@123 \
  --authentication-type password
```

---

## 3. Criação das Regras NSG (Segurança)

### Liberação da porta 8080

```bash
az network nsg rule create \
  --resource-group rg-vm-challenge \
  --nsg-name vm-challengeNSG \
  --name allow_port_8080 \
  --priority 1010 \
  --direction Inbound \
  --access Allow \
  --protocol Tcp \
  --destination-port-ranges 8080 \
  --source-address-prefixes 0.0.0.0/0 \
  --destination-address-prefixes 0.0.0.0/0
```

### Liberação da porta 80 (HTTP)

```bash
az network nsg rule create \
  --resource-group rg-vm-challenge \
  --nsg-name vm-challengeNSG \
  --name allow_http \
  --priority 1020 \
  --direction Inbound \
  --access Allow \
  --protocol Tcp \
  --destination-port-ranges 80 \
  --source-address-prefixes 0.0.0.0/0 \
  --destination-address-prefixes 0.0.0.0/0
```

---

## 4. Instalação do Docker na VM

```bash
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo \"${UBUNTU_CODENAME:-$VERSION_CODENAME}\") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-compose-plugin
```

---

## 5. Dockerfile da Aplicação

```dockerfile
FROM eclipse-temurin:17-jre-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

RUN chown appuser:appgroup /app

COPY --chown=appuser:appgroup SYSTRACK/target/sys-0.0.1-SNAPSHOT.jar app.jar

USER appuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

---

## 6. Build da Imagem Docker

```bash
sudo docker build -t systrack-api:1.0 .
```

---

## 7. Publicação no Docker Hub

### Tag da Imagem

```bash
sudo docker tag systrack-api:1.0 davidrapeckman/systrack-api:1.0
```

### Login no Docker Hub

```bash
sudo docker login
```

### Push da Imagem

```bash
sudo docker push davidrapeckman/systrack-api:1.0
```

---

## 8. Execução da Imagem na VM

```bash
sudo docker run -d -p 8080:8080 davidrapeckman/systrack-api:1.0
```

---

## 9. Acesso à Aplicação

Acesse a aplicação via navegador pelo IP público da VM:

```
http://<IP_DA_VM>:8080
```

Exemplo:

```
http://4.201.131.155:8080
```
