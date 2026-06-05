export interface Match {
  id: number;
  // homeTeam: Team;
  // awayTeam: Team;
  kickoff: Date;
  homeScore?: number;
  awayScore?: number;
  finished: boolean;
}
