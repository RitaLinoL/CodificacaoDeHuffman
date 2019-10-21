import os

for x in range(1,11):
	os.system("java -jar ../out/artifacts/CodificacaoDeHuffman_jar/CodificacaoDeHuffman.jar compress teste"+str(x)+".txt 'comprimidos/arquivo"+str(x)+".edz' 'comprimidos/tabela"+str(x)+".edt'")

for x in range(1,11):
	os.system("java -jar ../out/artifacts/CodificacaoDeHuffman_jar/CodificacaoDeHuffman.jar extract 'comprimidos/arquivo"+str(x)+".edz' 'comprimidos/tabela"+str(x)+".edt' 'gerados/teste"+str(x)+".txt'")


for x in range(1,11):
	os.system("diff 'gerados/teste"+str(x)+".txt' 'teste"+str(x)+".txt'")

