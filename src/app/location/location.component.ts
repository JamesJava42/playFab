import { Component, OnInit } from '@angular/core';
import { LocationResponse } from '../models/locationresponse';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  long:any=0;
    lat:any=0;
  ngOnInit(): void {
    
  }
  LocationComponent(){}
result?: LocationResponse;

  getLocation(){
   

  const getLocation = () => {
    const status:any = document.querySelector('.status');

    const sucess = (position: any)=>{
      console.log(position);
      this.result = position;
      console.log(this.result?.coords.latitude);

      this.long=this.result?.coords.longitude;
      this.lat= this.result?.coords.latitude;
      console.log("My long and lat : "+this.lat+ this.long);
      
    }
    

    const error = () => {
      status.textContent = "unable to locate add";

    }
    navigator.geolocation.getCurrentPosition(sucess,error);
  }
  document.querySelector('.find-state')?.addEventListener('click', getLocation);

 

}

}
