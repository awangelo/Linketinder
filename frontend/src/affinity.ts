export function calculateAffinity(candidatoSkills: string[], empresaSkills: string[]): number {
  if (empresaSkills.length === 0) return 0
  
  const commonSkills = candidatoSkills.filter(skill => empresaSkills.includes(skill)).length
  return Math.round((commonSkills / empresaSkills.length) * 100)
}
