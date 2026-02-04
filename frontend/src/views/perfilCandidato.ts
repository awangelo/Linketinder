import { Storage } from '../storage.js'
import { calculateAffinity } from '../affinity.js'

export function renderPerfilCandidato(): string {
  const vagas = Storage.getVagas()
  const empresas = Storage.getEmpresas()
  const currentUser = Storage.getCurrentUser()
  
  if (vagas.length === 0) {
    return `
      <div class="card">
        <h2>Vagas Disponíveis</h2>
        <div class="empty-state">
          <p>Nenhuma vaga cadastrada ainda.</p>
          <p>Empresas podem cadastrar vagas em <a href="#/cadastro/empresa">Cadastrar Empresa</a>.</p>
        </div>
      </div>
    `
  }
  
  const vagasHtml = vagas.map(v => {
    const empresa = empresas.find(e => e.id === v.empresaId)
    let affinityText = ''
    if (currentUser && currentUser.type === 'candidato') {
      const candidato = Storage.getCandidatos().find(c => c.id === currentUser.id)
      if (candidato && empresa) {
        const affinity = calculateAffinity(candidato.competencias, empresa.competencias)
        affinityText = `<br><small>Afinidade: ${affinity}%</small>`
      }
    }
    return `
      <tr>
        <td>
          <strong>${v.nome}</strong>
          <br><small>${v.descricao}</small>
          ${affinityText}
        </td>
        <td class="tooltip">
          Empresa Anônima
          <div class="tooltip-content">
            Estado: ${empresa?.estado || 'N/A'}<br>
            Descrição: ${empresa?.descricao || 'N/A'}
          </div>
        </td>
        <td>
          <div class="competencias">
            ${v.competencias.map(c => `<span class="tag">${c}</span>`).join('')}
          </div>
        </td>
        <td>
          <button class="danger" onclick="window.deleteVaga(${v.id})">Excluir</button>
        </td>
      </tr>
    `
  }).join('')
  
  return `
    <div class="card">
      <h2>Vagas Disponíveis</h2>
      <p><em>Nomes das empresas são revelados apenas após match</em></p>
      <table>
        <thead>
          <tr>
            <th>Vaga</th>
            <th>Empresa</th>
            <th>Competências</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          ${vagasHtml}
        </tbody>
      </table>
    </div>
  `
}

export function setupPerfilCandidato(): void {
  (window as any).deleteVaga = (id: number) => {
    if (confirm('Deseja realmente excluir esta vaga?')) {
      Storage.deleteVaga(id)
      // Recarrega
      const app = document.getElementById('app')!
      app.innerHTML = renderPerfilCandidato()
      setupPerfilCandidato()
    }
  }
}
