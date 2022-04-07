# TP Compilation : Génération d'arbres abstraits
> BAJT Arthur & LAFFONT Léo

---

# TP 2

# Exercice 1 :
On a décider de creer 2 classes :
- TreeNode : qui représente un noeud à deux sous arbres et a une operation.
- Leaf : qui est une feuille seule. Aka une valeur seule sans sous arbre ou operation.

tester :
```shell
java -cp .\build\libs\I805_TP2_Lambada.jar fr.usmb.m1isc.compilation.tp.Main .\test1.txt
```

---
# Exercice 2 :

Nous avons simplement eu a implémenter les résultats dans le cup. 
Cependant on ne peut pour l'instant pas vérifier la cohérence du code.

tester :
```shell
java -cp .\build\libs\I805_TP2_Lambada.jar fr.usmb.m1isc.compilation.tp.Main .\test2.txt
```


# TP 3 & 4

Apres avoir creer l'abre d'un code, nous générons maintenant le code 
de celui ci.

Pour avoir le code d'un arbre sous forme de *String* on appel `compile()` 
sur la racine de l'arbre. 
Ce code est ensuite mis dans le fichier `code.asm` .


Tester :
````shell
java -cp .\build\libs\I805_TP2_Lambada.jar fr.usmb.m1isc.compilation.tp.Main .\test2.txt
java -jar vm-0.9.jar code.asm --debug
````
---