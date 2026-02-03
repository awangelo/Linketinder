import { Storage } from './storage.js'

// Chart.js esta como CDN
declare const Chart: any

let chartInstance: any = null

export function renderCompetenciasChart(canvasId: string): void {
  const candidatos = Storage.getCandidatos()
  
  // Competencia x Candidatos
  const contagem: Record<string, number> = {}
  
  candidatos.forEach(c => {
    c.competencias.forEach(comp => {
      contagem[comp] = (contagem[comp] || 0) + 1
    })
  })
  
  const labels = Object.keys(contagem)
  const data = Object.values(contagem)
  
  // Destruir gráfico anterior se existir
  if (chartInstance) {
    chartInstance.destroy()
  }
  
  const canvas = document.getElementById(canvasId) as HTMLCanvasElement
  if (!canvas) return
  
  chartInstance = new Chart(canvas, {
    type: 'bar',
    data: {
      labels,
      datasets: [{
        label: 'Candidatos por Competência',
        data,
        backgroundColor: '#2563eb',
        borderRadius: 4
      }]
    },
    options: {
      responsive: true,
      plugins: {
        tooltip: {
          callbacks: {
            label: (context: any) => {
              const count = context.raw
              return `${count} candidato${count !== 1 ? 's' : ''}`
            }
          }
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          ticks: { stepSize: 1 }
        }
      }
    }
  })
}
