import { Storage } from '../storage.js'

export function renderHome(): string {
  const currentUser = Storage.getCurrentUser()
  const candidatos = Storage.getCandidatos()
  const empresas = Storage.getEmpresas()
  
  let userSelect = ''
  if (candidatos.length > 0 || empresas.length > 0) {
    userSelect = `
      <div class="card">
        <h3>Selecionar Usuário</h3>
        <form id="user-select-form">
          <select id="user-select" required>
            <option value="">Escolha um usuário...</option>
            ${candidatos.map(c => `<option value="candidato-${c.id}">${c.nome} (Candidato)</option>`).join('')}
            ${empresas.map(e => `<option value="empresa-${e.id}">${e.nome} (Empresa)</option>`).join('')}
          </select>
          <button type="submit">Selecionar</button>
        </form>
        ${currentUser ? `<p>Usuário atual: ${currentUser.type === 'candidato' ? 'Candidato' : 'Empresa'} ID ${currentUser.id}</p>` : ''}
      </div>
    `
  }
  
  return `
    <div class="card">
      <h2>Bem-vindo!</h2>
      <p>Conectando candidatos e empresas de forma anônima.</p>
      
      <div class="home-links">
        <a href="#/cadastro/candidato" class="home-link">
          Cadastrar Candidato
        </a>
        <a href="#/cadastro/empresa" class="home-link">
          Cadastrar Empresa
        </a>
        <a href="#/perfil/candidato" class="home-link">
          Ver Vagas
        </a>
        <a href="#/perfil/empresa" class="home-link">
          Ver Candidatos
        </a>
      </div>
    </div>
    ${userSelect}
  `
}

export function setupHome(): void {
  const form = document.getElementById('user-select-form') as HTMLFormElement
  if (!form) return
  
  form.addEventListener('submit', (e) => {
    e.preventDefault()
    const select = document.getElementById('user-select') as HTMLSelectElement
    const value = select.value
    if (!value) return
    
    const [type, idStr] = value.split('-')
    const id = parseInt(idStr)
    
    Storage.setCurrentUser({ type: type as 'candidato' | 'empresa', id })
    
    // Re-render
    const app = document.getElementById('app')!
    app.innerHTML = renderHome()
    setupHome()
  })
}
