import { Component, inject, OnInit, output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { OtherService } from '../../../services/other-service';

@Component({
  selector: 'app-details-editor',
  imports: [MatButtonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule],
  templateUrl: './details-editor.html',
  styleUrl: './details-editor.scss',
})
export class DetailsEditor implements OnInit {
  private otherService = inject(OtherService)
  close = output()
  detailsForm!: FormGroup;
  openingDetailsForm!: FormGroup;
  dayNames = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat", "Vasárnap"]

  ngOnInit(): void {
    //Adatok lekérése
    this.otherService.getDetails().subscribe({
      next: response => {

      },
      error: error => console.log(error)
    })

    this.otherService.getOpeningDetails().subscribe({
      next: response => {

      },
      error: error => console.log(error)
    })

    //Formok:
    this.detailsForm = new FormGroup({
      address: new FormControl("", [Validators.required]),
      phone: new FormControl("", [Validators.required]),
      email: new FormControl("", [Validators.required, Validators.email]),
      firePhone: new FormControl("", [Validators.required])
    })


  }

  saveDetails() {

  }
}
