export function renderHome(): string {
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
  `
}
