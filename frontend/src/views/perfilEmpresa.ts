import { Storage } from '../storage.js'
import { calculateAffinity } from '../affinity.js'
import { renderCompetenciasChart } from '../charts.js'

export function renderPerfilEmpresa(): string {
  const candidatos = Storage.getCandidatos()
  const currentUser = Storage.getCurrentUser()
  
  if (candidatos.length === 0) {
    return `
      <div class="card">
        <h2>Candidatos Disponíveis</h2>
        <div class="empty-state">
          <p>Nenhum candidato cadastrado ainda.</p>
          <p>Candidatos podem se cadastrar em <a href="#/cadastro/candidato">Cadastrar Candidato</a>.</p>
        </div>
      </div>
      <div class="card">
        <h2>Candidatos por Competência</h2>
        <div class="empty-state">
          <p>Cadastre candidatos para visualizar o gráfico.</p>
        </div>
      </div>
    `
  }
  
  const candidatosHtml = candidatos.map(c => {
    let affinityText = ''
    if (currentUser && currentUser.type === 'empresa') {
      const empresa = Storage.getEmpresas().find(e => e.id === currentUser.id)
      if (empresa) {
        const affinity = calculateAffinity(c.competencias, empresa.competencias)
        affinityText = `<br><small>Afinidade: ${affinity}%</small>`
      }
    }
    return `
      <tr>
        <td class="tooltip">
          Candidato Anônimo
          <div class="tooltip-content">
            Idade: ${c.idade} anos<br>
            Estado: ${c.estado}<br>
            Descrição: ${c.descricao}
          </div>
        </td>
        <td>
          <div class="competencias">
            ${c.competencias.map(comp => `<span class="tag">${comp}</span>`).join('')}
          </div>
        </td>
        <td>${c.descricao.substring(0, 50)}${c.descricao.length > 50 ? '...' : ''}${affinityText}</td>
        <td>
          <button class="danger" onclick="window.deleteCandidato(${c.id})">Excluir</button>
        </td>
      </tr>
    `
  }).join('')
  
  return `
    <div class="card">
      <h2>Candidatos Disponíveis</h2>
      <p><em>Nomes dos candidatos são revelados apenas após match</em></p>
      <table>
        <thead>
          <tr>
            <th>Candidato</th>
            <th>Competências</th>
            <th>Descrição</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          ${candidatosHtml}
        </tbody>
      </table>
    </div>
    
    <div class="card">
      <h2>Candidatos por Competência</h2>
      <div class="chart-container">
        <canvas id="chart-competencias"></canvas>
      </div>
    </div>
  `
}

export function setupPerfilEmpresa(): void {
  // Setup delete function
  (window as any).deleteCandidato = (id: number) => {
    if (confirm('Deseja realmente excluir este candidato?')) {
      Storage.deleteCandidato(id)
      // Re-render
      const app = document.getElementById('app')!
      app.innerHTML = renderPerfilEmpresa()
      setupPerfilEmpresa()
    }
  }
  
  // Render chart
  setTimeout(() => {
    renderCompetenciasChart('chart-competencias')
  }, 100)
}
