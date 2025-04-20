import { Component, OnInit } from '@angular/core';
import { FlagsService } from '../../../services/flags.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

interface Flags {
  id?: number;
  flags?: string;
}

@Component({
  selector: 'app-flags',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './flags.component.html',
  styleUrl: './flags.component.css'
})
export class FlagsComponent implements OnInit {
  flags: Flags[] = [];
  showModal: boolean = false;
  isEditing: boolean = false;
  selectedFlag: Flags = { flags: '' };

  constructor(private flagsService: FlagsService) {}

  ngOnInit(): void {
    this.getFlags();
  }

  getFlags(): void {
    this.flagsService.getFlags().subscribe(data => {
      this.flags = data;
    });
  }

  openModal(flag?: Flags): void {
    this.showModal = true;
    this.isEditing = !!flag;
    this.selectedFlag = flag ? { ...flag } : { flags: '' };
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedFlag = { flags: '' };
  }

  saveFlag(): void {
    if (this.isEditing && this.selectedFlag.id) {
      this.flagsService.updateFlag(this.selectedFlag.id, this.selectedFlag).subscribe(() => {
        this.getFlags();
        this.closeModal();
      });
    } else {
      this.flagsService.createFlag(this.selectedFlag).subscribe(() => {
        this.getFlags();
        this.closeModal();
      });
    }
  }

  deleteFlag(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta Flag?')) {
      this.flagsService.deleteFlag(id).subscribe(() => {
        this.getFlags();
      });
    }
  }
}
