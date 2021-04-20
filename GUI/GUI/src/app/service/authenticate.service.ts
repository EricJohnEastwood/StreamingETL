import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor() { }

  authenticate(username: string, password:string){
    if(username === "ETL" && password==="ETL")
    {
      sessionStorage.setItem('username', username);
      return true;
    }
    else
    {
      return false;
    }
  }

  isUserLoggedIn(){
    let user = sessionStorage.getItem('username');
    return !(user===null);
  }

  logout(){
    sessionStorage.removeItem('username');
  }
}
