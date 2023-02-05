$makeDir=Get-Location

for ($i = 0; $i -lt $args.Count; $i++)
{
    $blockName=$args[$i]
    
    #blockState
        $blockStatePath="$makeDir\assets\enderioremake\blockstates\block_alloy_$blockName.json"
        New-Item -Path $blockStatePath
        Out-File -FilePath $blockStatePath -InputObject "{`n `"variants`": {`n  `"`": {`"model`": `"enderioremake:block/block_alloy_$blockName`"}`n }`n}" -Encoding ASCII

    #blockModel
        $blockModelPath="$makeDir\assets\enderioremake\models\block\block_alloy_$blockName.json"
        New-Item -Path $blockModelPath
        Out-File -FilePath $blockModelPath -InputObject "{`n `"parent`": `"block/cube_all`",`n `"textures`": {`n  `"all`": `"enderioremake:block/block_alloy_$blockName`"`n }`n}" -Encoding ASCII

    #item
        $blockItemPath="$makeDir\assets\enderioremake\models\item\block_alloy_$blockName.json"
        New-Item -Path $blockItemPath
        Out-File -FilePath $blockItemPath -InputObject "{`n `"parent`": `"enderioremake:block/block_alloy_$blockName`"`n}" -Encoding ASCII

    #lootTable
        $blockLootTablePath="$makeDir\data\enderioremake\loot_tables\blocks\block_alloy_$blockName.json"
        New-Item -Path $blockLootTablePath
        Out-File -FilePath $blockLootTablePath -InputObject "{`n `"type`": `"minecraft:block`",`n `"pools`": [`n  {`n   `"rolls`": 1,`n   `"entries`": [`n    {`n     `"type`": `"minecraft:item`",`n     `"name`": `"enderioremake:block_alloy_$blockName`"`n    }`n   ]`n  }`n ]`n}"
}