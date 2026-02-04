import { renderHome, setupHome } from './views/home.js'
import { renderCadastroCandidato, setupCadastroCandidato } from './views/cadastroCandidato.js'
import { renderCadastroEmpresa, setupCadastroEmpresa } from './views/cadastroEmpresa.js'
import { renderPerfilCandidato, setupPerfilCandidato } from './views/perfilCandidato.js'
import { renderPerfilEmpresa, setupPerfilEmpresa } from './views/perfilEmpresa.js'

type Route = {
  render: () => string
  setup?: () => void
}

// Router
const routes: Record<string, Route> = {
  '/': { render: renderHome, setup: setupHome },
  '/cadastro/candidato': { render: renderCadastroCandidato, setup: setupCadastroCandidato },
  '/cadastro/empresa': { render: renderCadastroEmpresa, setup: setupCadastroEmpresa },
  '/perfil/candidato': { render: renderPerfilCandidato, setup: setupPerfilCandidato },
  '/perfil/empresa': { render: renderPerfilEmpresa, setup: setupPerfilEmpresa }
}

function getRoute(): string {
  return window.location.hash.slice(1) || '/'
}

function router(): void {
  const path = getRoute()
  const route = routes[path] || routes['/']
  
  const app = document.getElementById('app')!
  app.innerHTML = route.render()
  
  if (route.setup) {
    route.setup()
  }
}

window.addEventListener('hashchange', router) // Clicou em uma pagina
window.addEventListener('DOMContentLoaded', router)
