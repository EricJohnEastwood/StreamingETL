import { Component, OnInit } from '@angular/core';
import {ETL, HttpClientService} from '../service/http-client.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  etl:ETL[];

  constructor(private httpClientService:HttpClientService) { }

  ngOnInit(): void {
    this.httpClientService.getMessage().subscribe(
      (response) => this.handleSuccessfulResponse(response)
    );
  }

  deleteSource(e: ETL): void {
    this.httpClientService.deleteSource(e)
      .subscribe( data => {
        this.etl = this.etl.filter(u => u !== e);
      })
  };

  handleSuccessfulResponse(response){
    this.etl = response;
    //console.log(response);
  }
}
