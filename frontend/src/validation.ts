export const TAGS_PADRONIZADAS = [
  'JavaScript', 'TypeScript', 'Python', 'Java', 'C#', 'C++', 'PHP', 'Ruby',
  'Go', 'Rust', 'Swift', 'Kotlin', 'React', 'Angular', 'Vue.js', 'Node.js',
  'Express', 'Django', 'Spring', 'Laravel', 'SQL', 'MongoDB', 'PostgreSQL',
  'MySQL', 'Git', 'Docker', 'AWS', 'Azure', 'Linux', 'HTML', 'CSS', 'Sass'
]

export function validateNome(value: string): string | null {
  const regex = /^[a-zA-ZÀ-ÿ\s]{2,50}$/
  if (!regex.test(value)) {
    return 'Nome deve conter apenas letras e espaços, entre 2 e 50 caracteres.'
  }
  return null
}

export function validateEmail(value: string): string | null {
  const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/ // Ignora caracteres whitespace e verifica formato
  if (!regex.test(value)) {
    return 'E-mail inválido.'
  }
  return null
}

export function validateCPF(value: string): string | null {
  const regex = /^(\d{3}\.\d{3}\.\d{3}-\d{2}|\d{11})$/
  if (!regex.test(value)) {
    return 'CPF deve estar no formato XXX.XXX.XXX-XX ou 11 dígitos.'
  }
  return null
}

export function validateTelefone(value: string): string | null {
  const regex = /^\(\d{2}\)\s?\d{4,5}-?\d{4}$/
  if (!regex.test(value)) {
    return 'Telefone deve estar no formato (XX) XXXXX-XXXX (espaço e hífen opcionais).'
  }
  return null
}

export function validateLinkedIn(value: string): string | null {
  const regex = /^https:\/\/(www\.)?linkedin\.com\/in\/[\w-]+$/
  if (!regex.test(value)) {
    return 'Link do LinkedIn deve ser uma URL válida do LinkedIn.'
  }
  return null
}

export function validateCNPJ(value: string): string | null {
  const regex = /^(\d{2}\.\d{3}\.\d{3}\/\d{4}-\d{2}|\d{14})$/
  if (!regex.test(value)) {
    return 'CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX ou 14 dígitos.'
  }
  return null
}

export function validateCEP(value: string): string | null {
  const regex = /^(\d{5}-\d{3}|\d{8})$/
  if (!regex.test(value)) {
    return 'CEP deve estar no formato XXXXX-XXX ou 8 dígitos.'
  }
  return null
}

export function validateTags(tags: string[]): string | null {
  if (tags.length === 0) {
    return 'Selecione pelo menos uma competência.'
  }
  for (const tag of tags) {
    if (!TAGS_PADRONIZADAS.includes(tag)) {
      return `Competência "${tag}" não é válida.`
    }
  }
  return null
}
