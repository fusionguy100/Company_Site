import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAnnouncementModal } from './create-announcement-modal';

describe('CreateAnnouncementModal', () => {
  let component: CreateAnnouncementModal;
  let fixture: ComponentFixture<CreateAnnouncementModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateAnnouncementModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateAnnouncementModal);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
