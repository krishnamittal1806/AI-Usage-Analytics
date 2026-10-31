$models = "gpt-x","gpt-y","claude-z"
$fmt = "yyyy-MM-dd'T'HH:mm:ss'Z'"
function Send($obj) {
  Invoke-RestMethod -Uri http://localhost:8080/events -Method Post `
    -ContentType "application/json" -Body ($obj | ConvertTo-Json -Depth 5) | Out-Null
}
1..30 | ForEach-Object {
  Send @{ eventType="AI_RESPONSE"; userId="u$(Get-Random -Min 1 -Max 5)"
          timestamp=[DateTime]::UtcNow.AddDays(-(Get-Random -Max 3)).ToString($fmt)
          metadata=@{ requestId="seed-$_"; model=($models | Get-Random)
                      inputTokens=(Get-Random -Min 50 -Max 500)
                      outputTokens=(Get-Random -Min 100 -Max 1000)
                      latencyMs=(Get-Random -Min 200 -Max 2500) } }
}
1..5 | ForEach-Object {
  Send @{ eventType="AI_ERROR"; userId="u1"
          metadata=@{ requestId="seed-err-$_"; model=($models | Get-Random); errorType="timeout" } }
}