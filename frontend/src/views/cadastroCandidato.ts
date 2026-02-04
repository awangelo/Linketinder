import { Storage } from '../storage.js'
import { validateNome, validateEmail, validateCPF, validateTelefone, validateLinkedIn, validateCEP, validateTags, TAGS_PADRONIZADAS } from '../validation.js'

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
          <input type="text" id="cpf" required placeholder="000.000.000-00 ou 00000000000">
        </div>
        <div class="form-group">
          <label for="telefone">Telefone</label>
          <input type="text" id="telefone" required placeholder="(11) 99999-9999">
        </div>
        <div class="form-group">
          <label for="linkedin">LinkedIn</label>
          <input type="url" id="linkedin" required placeholder="https://linkedin.com/in/perfil">
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
          <label for="competencias">Competências</label>
          <select id="competencias" multiple required>
            ${TAGS_PADRONIZADAS.map(tag => `<option value="${tag}">${tag}</option>`).join('')}
          </select>
          <small>Ctrl + Click para selecionar múltiplas</small>
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
    const getSelectValues = (id: string) => {
      const select = document.getElementById(id) as HTMLSelectElement
      return Array.from(select.selectedOptions).map(option => option.value)
    }
    
    const nome = getValue('nome')
    const email = getValue('email')
    const cpf = getValue('cpf')
    const telefone = getValue('telefone')
    const linkedin = getValue('linkedin')
    const idade = parseInt(getValue('idade'))
    const estado = getValue('estado')
    const cep = getValue('cep')
    const descricao = getValue('descricao')
    const competencias = getSelectValues('competencias')
    
    const nomeError = validateNome(nome)
    if (nomeError) { alert(`Erro no nome: ${nomeError}`); return }
    
    const emailError = validateEmail(email)
    if (emailError) { alert(`Erro no e-mail: ${emailError}`); return }
    
    const cpfError = validateCPF(cpf)
    if (cpfError) { alert(`Erro no CPF: ${cpfError}`); return }
    
    const telefoneError = validateTelefone(telefone)
    if (telefoneError) { alert(`Erro no telefone: ${telefoneError}`); return }
    
    const linkedinError = validateLinkedIn(linkedin)
    if (linkedinError) { alert(`Erro no LinkedIn: ${linkedinError}`); return }
    
    const cepError = validateCEP(cep)
    if (cepError) { alert(`Erro no CEP: ${cepError}`); return }
    
    const tagsError = validateTags(competencias)
    if (tagsError) { alert(`Erro nas competências: ${tagsError}`); return }
    
    const candidato = {
      nome,
      email,
      cpf,
      telefone,
      linkedin,
      idade,
      estado,
      cep,
      descricao,
      competencias
    }
    
    Storage.saveCandidato(candidato)
    
    const msg = document.getElementById('mensagem')!
    msg.className = 'success'
    msg.textContent = 'Candidato cadastrado com sucesso!'
    form.reset()
  })
}
