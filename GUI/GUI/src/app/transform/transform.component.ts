import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

import * as ace from 'ace-builds';
import 'ace-builds/src-noconflict/mode-javascript';
import 'ace-builds/src-noconflict/theme-github';
import { HttpClientService,Transform } from '../service/http-client.service';

const THEME = 'ace/theme/github'; 

@Component({
  selector: 'app-transform',
  templateUrl: './transform.component.html',
  styleUrls: ['./transform.component.css']
})
export class TransformComponent implements OnInit {

    @ViewChild('codeEditor', {static: true}) codeEditorElmRef: ElementRef;
    private codeEditor: ace.Ace.Editor;

    transform:Transform = new Transform("");

    constructor(private httpClientService:HttpClientService) { }

    ngOnInit () {
        const element = this.codeEditorElmRef.nativeElement;
        const editorOptions: Partial<ace.Ace.EditorOptions> = {
            highlightActiveLine: true,
            minLines: 10,
            maxLines: Infinity,
        };

        this.codeEditor = ace.edit(element, editorOptions);
        this.codeEditor.setTheme(THEME);
        this.codeEditor.setShowFoldWidgets(true); // for the scope fold feature
        this.httpClientService.getTransform().subscribe(
          response => {this.handleSuccessfulResponse(response)}
        );
     }

     getCode() : void{
       this.transform.transform = this.codeEditor.getValue();
       //console.log(typeof(transform));
       this.httpClientService.addTransform(this.transform).subscribe(
         data => {alert("Transformation added successfully");}
       )
     }

     clear() : void{
      this.codeEditor.setValue("");
     }

     handleSuccessfulResponse(response){
        response.forEach(element => {
          this.codeEditor.setValue(element.transform);
        });
     }
}
