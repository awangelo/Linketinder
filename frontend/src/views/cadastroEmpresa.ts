import { Storage } from '../storage.js'
import { validateNome, validateEmail, validateCNPJ, validateCEP, validateTags, TAGS_PADRONIZADAS } from '../validation.js'

export function renderCadastroEmpresa(): string {
  return `
    <div class="card">
      <h2>Cadastro de Empresa</h2>
      <form id="form-empresa">
        <div class="form-group">
          <label for="nome">Nome da Empresa</label>
          <input type="text" id="nome" required>
        </div>
        <div class="form-group">
          <label for="email">E-mail</label>
          <input type="email" id="email" required>
        </div>
        <div class="form-group">
          <label for="cnpj">CNPJ</label>
          <input type="text" id="cnpj" required placeholder="00.000.000/0000-00">
        </div>
        <div class="form-group">
          <label for="pais">País</label>
          <input type="text" id="pais" required value="Brasil">
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
          <label for="descricao">Descrição da Empresa</label>
          <textarea id="descricao" rows="3" required></textarea>
        </div>
        <div class="form-group">
          <label for="competencias">Competências buscadas</label>
          <select id="competencias" multiple required>
            ${TAGS_PADRONIZADAS.map(tag => `<option value="${tag}">${tag}</option>`).join('')}
          </select>
          <small>Ctrl + Click para selecionar múltiplas</small>
        </div>
        <button type="submit">Cadastrar Empresa</button>
        <div id="mensagem"></div>
      </form>
    </div>

    <div class="card">
      <h2>Cadastrar Vaga</h2>
      <form id="form-vaga">
        <div class="form-group">
          <label for="empresa-select">Empresa</label>
          <select id="empresa-select" required>
            <option value="">Selecione uma empresa...</option>
          </select>
        </div>
        <div class="form-group">
          <label for="vaga-nome">Nome da Vaga</label>
          <input type="text" id="vaga-nome" required placeholder="Desenvolvedor Full Stack">
        </div>
        <div class="form-group">
          <label for="vaga-descricao">Descrição</label>
          <textarea id="vaga-descricao" rows="3" required></textarea>
        </div>
        <div class="form-group">
          <label for="vaga-competencias">Competências (separadas por vírgula)</label>
          <input type="text" id="vaga-competencias" required placeholder="React, Node.js, PostgreSQL">
        </div>
        <button type="submit">Cadastrar Vaga</button>
        <div id="mensagem-vaga"></div>
      </form>
    </div>
  `
}

export function setupCadastroEmpresa(): void {
  // Form Empresa
  const formEmpresa = document.getElementById('form-empresa') as HTMLFormElement
  if (formEmpresa) {
    formEmpresa.addEventListener('submit', (e) => {
      e.preventDefault()
    
      const getValue = (id: string) => (document.getElementById(id) as HTMLInputElement).value.trim()
      const getSelectValues = (id: string) => {
        const select = document.getElementById(id) as HTMLSelectElement
        return Array.from(select.selectedOptions).map(option => option.value)
      }
      
      const nome = getValue('nome')
      const email = getValue('email')
      const cnpj = getValue('cnpj')
      const pais = getValue('pais')
      const estado = getValue('estado')
      const cep = getValue('cep')
      const descricao = getValue('descricao')
      const competencias = getSelectValues('competencias')
      
      // Validações
      const nomeError = validateNome(nome)
      if (nomeError) { alert(`Erro no nome: ${nomeError}`); return }
      
      const emailError = validateEmail(email)
      if (emailError) { alert(`Erro no e-mail: ${emailError}`); return }
      
      const cnpjError = validateCNPJ(cnpj)
      if (cnpjError) { alert(`Erro no CNPJ: ${cnpjError}`); return }
      
      const cepError = validateCEP(cep)
      if (cepError) { alert(`Erro no CEP: ${cepError}`); return }
      
      const tagsError = validateTags(competencias)
      if (tagsError) { alert(`Erro nas competências: ${tagsError}`); return }
      
      const empresa = {
        nome,
        email,
        cnpj,
        pais,
        estado,
        cep,
        descricao,
        competencias
      }
      
      Storage.saveEmpresa(empresa)
      
      const msg = document.getElementById('mensagem')!
      msg.className = 'success'
      msg.textContent = 'Empresa cadastrada com sucesso!'
      formEmpresa.reset()
      
      // Atualiza select de empresas
      loadEmpresas()
    })
  }

  // Form Vaga
  const formVaga = document.getElementById('form-vaga') as HTMLFormElement
  if (formVaga) {
    formVaga.addEventListener('submit', (e) => {
      e.preventDefault()
      
      const empresaId = parseInt((document.getElementById('empresa-select') as HTMLSelectElement).value)
      const nome = (document.getElementById('vaga-nome') as HTMLInputElement).value.trim()
      const descricao = (document.getElementById('vaga-descricao') as HTMLTextAreaElement).value.trim()
      const competencias = (document.getElementById('vaga-competencias') as HTMLInputElement).value
        .split(',').map(c => c.trim()).filter(c => c)
      
      Storage.saveVaga({ empresaId, nome, descricao, competencias })
      
      const msg = document.getElementById('mensagem-vaga')!
      msg.className = 'success'
      msg.textContent = 'Vaga cadastrada com sucesso!'
      formVaga.reset()
    })
  }

  loadEmpresas()
}

function loadEmpresas(): void {
  const select = document.getElementById('empresa-select') as HTMLSelectElement
  if (!select) return
  
  const empresas = Storage.getEmpresas()
  select.innerHTML = '<option value="">Selecione uma empresa...</option>' +
    empresas.map(e => `<option value="${e.id}">${e.nome}</option>`).join('')
}
