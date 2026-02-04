import { Candidato, Empresa, Vaga, CurrentUser } from './models.js'

export namespace Storage {
  const KEYS = {
    candidatos: 'linketinder_candidatos',
    empresas: 'linketinder_empresas',
    vagas: 'linketinder_vagas',
    currentUser: 'linketinder_current_user'
  }

  // Candidatos
  export function getCandidatos(): Candidato[] {
    const data = localStorage.getItem(KEYS.candidatos)
    return data ? JSON.parse(data) : []
  }

  // Omit constroi um valor do tipo T sem o campo K
  export function saveCandidato(candidato: Omit<Candidato, 'id'>): Candidato {
    const candidatos = getCandidatos()
    const novo: Candidato = { ...candidato, id: Date.now() }
    candidatos.push(novo)
    localStorage.setItem(KEYS.candidatos, JSON.stringify(candidatos))
    return novo
  }

  export function deleteCandidato(id: number): void {
    const candidatos = getCandidatos().filter(c => c.id !== id)
    localStorage.setItem(KEYS.candidatos, JSON.stringify(candidatos))
  }

  // Empresas
  export function getEmpresas(): Empresa[] {
    const data = localStorage.getItem(KEYS.empresas)
    return data ? JSON.parse(data) : []
  }

  export function saveEmpresa(empresa: Omit<Empresa, 'id'>): Empresa {
    const empresas = getEmpresas()
    const nova: Empresa = { ...empresa, id: Date.now() }
    empresas.push(nova)
    localStorage.setItem(KEYS.empresas, JSON.stringify(empresas))
    return nova
  }

  export function deleteEmpresa(id: number): void {
    const empresas = getEmpresas().filter(e => e.id !== id)
    localStorage.setItem(KEYS.empresas, JSON.stringify(empresas))
  }

  // Vagas
  export function getVagas(): Vaga[] {
    const data = localStorage.getItem(KEYS.vagas)
    return data ? JSON.parse(data) : []
  }

  export function saveVaga(vaga: Omit<Vaga, 'id'>): Vaga {
    const vagas = getVagas()
    const nova: Vaga = { ...vaga, id: Date.now() }
    vagas.push(nova)
    localStorage.setItem(KEYS.vagas, JSON.stringify(vagas))
    return nova
  }

  export function deleteVaga(id: number): void {
    const vagas = getVagas().filter(v => v.id !== id)
    localStorage.setItem(KEYS.vagas, JSON.stringify(vagas))
  }

  // Current User
  export function getCurrentUser(): CurrentUser | null {
    const data = localStorage.getItem(KEYS.currentUser)
    return data ? JSON.parse(data) : null
  }

  export function setCurrentUser(user: CurrentUser): void {
    localStorage.setItem(KEYS.currentUser, JSON.stringify(user))
  }

  export function clearCurrentUser(): void {
    localStorage.removeItem(KEYS.currentUser)
  }
}
