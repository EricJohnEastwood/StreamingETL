import { Component, OnInit } from '@angular/core';
import { ETL, HttpClientService } from '../service/http-client.service';

@Component({
  selector: 'app-add-source',
  templateUrl: './add-source.component.html',
  styleUrls: ['./add-source.component.css']
})
export class AddSourceComponent implements OnInit {

  source: ETL = new ETL("","","");

  constructor(private httpClientService:HttpClientService) { }

  ngOnInit(): void {
  }

  addSource(): void{
    this.httpClientService.addSource(this.source).subscribe(
      data => {alert("Source added successfully.");}
    )
  }

}
