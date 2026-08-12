# Build the LaTeX PDF report
$ErrorActionPreference = "Stop"
$latexDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$texFile = Join-Path $latexDir "ASSIGNMENT_REPORT.tex"
$pdfBase = Join-Path $latexDir "RPSR_Ranasinghe_22020782_QA_Automation"

Push-Location $latexDir
try {
    pdflatex -interaction=nonstopmode -jobname="RPSR_Ranasinghe_22020782_QA_Automation" "ASSIGNMENT_REPORT.tex" | Out-Null
    pdflatex -interaction=nonstopmode -jobname="RPSR_Ranasinghe_22020782_QA_Automation" "ASSIGNMENT_REPORT.tex" | Out-Null

    $pdf = "$pdfBase.pdf"
    if (Test-Path $pdf) {
        Write-Host "PDF created: $pdf"
        Write-Host "Size: $([math]::Round((Get-Item $pdf).Length / 1KB, 1)) KB"
    } else {
        Write-Error "PDF was not generated. Check ASSIGNMENT_REPORT.log"
    }
} finally {
    Pop-Location
}
