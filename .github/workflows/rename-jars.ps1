$matrix_os = $args[0]
$timestamp = Get-Date -Format "yyMMdd_HHmmss"

Get-ChildItem -Path "target" -Filter "*.jar" | ForEach-Object {
    $originalName = $_.Name
    $newName = "${matrix_os}-$timestamp-$originalName"
    $newPath = Join-Path -Path "target" -ChildPath $newName

    if (-Not (Test-Path $newPath)) {
        Move-Item -LiteralPath $_.FullName -Destination $newPath -Verbose
    } else {
        Write-Host "File already exists: $newPath" -ForegroundColor Yellow
    }
}
