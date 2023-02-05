$makeDir=Get-Location

for ($i = 0; $i -lt $args.Count; $i++)
{
    $ingotName=$args[$i]
    #makeIngotRecipes
    $ingFromNuggPath="$makeDir\$ingotName"+"_from_nugget.json"
    New-Item -Path $ingFromNuggPath
    Out-File -FilePath $ingFromNuggPath -InputObject "{`n `"type`": `"minecraft:crafting_shaped`",`n `"pattern`": [`n  `"###`",`n  `"###`",`n  `"###`"`n ],`n`"key`": {`n  `"#`": {`n   `"item`": `"enderioremake:item_alloy_nugget_$ingotName`"`n  }`n },`n `"result`": {`n  `"item`": `"enderioremake:item_alloy_ingot_$ingotName`",`n  `"count`": 1`n },`n `"group`": `"enderioremake`"`n}" -Encoding ASCII
    
    $ingFromBlockPath="$makeDir\$ingotName"+"_from_block.json"
    New-Item -Path $ingFromBlockPath
    Out-File -FilePath $ingFromBlockPath -InputObject "{`n `"type`": `"minecraft:crafting_shapeless`",`n `"ingredients`": [`n  {`n   `"item`": `"enderioremake:block_alloy_$ingotName`"`n  }`n ],`n `"result`": {`n  `"item`": `"enderioremake:item_alloy_ingot_$ingotName`",`n  `"count`": 9`n },`n `"group`": `"enderioremake`"`n}" -Encoding ASCII

    #makeNuggetRecipes
    $nuggFromIngPath="$makeDir\$ingotName"+"_nugget_from_ingot.json"
    New-Item -Path $nuggFromIngPath
    Out-File -FilePath $nuggFromIngPath -InputObject "{`n `"type`": `"minecraft:crafting_shapeless`",`n `"ingredients`": [`n  {`n   `"item`": `"enderioremake:item_alloy_ingot_$ingotName`"`n  }`n ],`n `"result`": {`n  `"item`": `"enderioremake:item_alloy_nugget_$ingotName`",`n  `"count`": 9`n },`n `"group`": `"enderioremake`"`n}" -Encoding ASCII
}