import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PromptComponent } from '../_popups/prompt/prompt.component';

@Injectable({
  providedIn: 'root'
})
export class PromptService {

  constructor(
    private dialog: MatDialog
  ) { }

  openPrompt(promptData: any) {
    const dialogRef = this.dialog.open(PromptComponent, {
      data: promptData
    });

    return dialogRef.afterClosed();
  }
}
