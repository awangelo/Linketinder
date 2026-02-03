import { Storage } from '../storage.js'

export function renderCadastroCandidato(): string {
  return `
    <div class="card">
      <h2>Cadastro de Candidato</h2>
      <form id="form-candidato">
        <div class="form-group">
          <label for="nome">Nome</label>
          <input type="text" id="nome" required>
        </div>
        <div class="form-group">
          <label for="email">E-mail</label>
          <input type="email" id="email" required>
        </div>
        <div class="form-group">
          <label for="cpf">CPF</label>
          <input type="text" id="cpf" required placeholder="000.000.000-00">
        </div>
        <div class="form-group">
          <label for="idade">Idade</label>
          <input type="number" id="idade" required min="16" max="100">
        </div>
        <div class="form-group">
          <label for="estado">Estado</label>
          <input type="text" id="estado" required placeholder="SP">
        </div>
        <div class="form-group">
          <label for="cep">CEP</label>
          <input type="text" id="cep" required placeholder="00000-000">
        </div>
        <div class="form-group">
          <label for="descricao">Descrição pessoal</label>
          <textarea id="descricao" rows="3" required></textarea>
        </div>
        <div class="form-group">
          <label for="competencias">Competências (separadas por vírgula)</label>
          <input type="text" id="competencias" required placeholder="JavaScript, Python, SQL">
        </div>
        <button type="submit">Cadastrar</button>
        <div id="mensagem"></div>
      </form>
    </div>
  `
}

export function setupCadastroCandidato(): void {
  const form = document.getElementById('form-candidato') as HTMLFormElement
  if (!form) return

  form.addEventListener('submit', (e) => {
    e.preventDefault()
    
    const getValue = (id: string) => (document.getElementById(id) as HTMLInputElement).value.trim()
    
    const candidato = {
      nome: getValue('nome'),
      email: getValue('email'),
      cpf: getValue('cpf'),
      idade: parseInt(getValue('idade')),
      estado: getValue('estado'),
      cep: getValue('cep'),
      descricao: getValue('descricao'),
      competencias: getValue('competencias').split(',').map(c => c.trim()).filter(c => c)
    }
    
    Storage.saveCandidato(candidato)
    
    const msg = document.getElementById('mensagem')!
    msg.className = 'success'
    msg.textContent = 'Candidato cadastrado com sucesso!'
    form.reset()
  })
}
