import { Storage } from '../storage.js'

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
          <label for="competencias">Competências buscadas (separadas por vírgula)</label>
          <input type="text" id="competencias" required placeholder="JavaScript, Python, SQL">
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
      
      const empresa = {
        nome: getValue('nome'),
        email: getValue('email'),
        cnpj: getValue('cnpj'),
        pais: getValue('pais'),
        estado: getValue('estado'),
        cep: getValue('cep'),
        descricao: getValue('descricao'),
        competencias: getValue('competencias').split(',').map(c => c.trim()).filter(c => c)
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
