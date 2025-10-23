import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnouncementCard } from './announcement-card';

describe('AnnouncementCard', () => {
  let component: AnnouncementCard;
  let fixture: ComponentFixture<AnnouncementCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnouncementCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnouncementCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
