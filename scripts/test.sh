#!/bin/bash
week=$(date +%U)
if [ $(($week % 2)) == 0 ]; then 
    echo even week, do nothing
    exit
else 
    echo odd week, cut release branch
fi

