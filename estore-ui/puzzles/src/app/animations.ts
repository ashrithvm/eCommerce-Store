import {style, transition, trigger,  group, animate, state} from "@angular/animations";

export const fadeInOutAnimation = trigger('fadeInOut', [

  // end state styles for route container (host)
  state('*', style({
    // the view covers the whole screen with a semi tranparent background

  })),

  // route 'enter' transition
  transition(':enter', [

    // styles at start of transition
    style({
      // start with the content positioned off the right of the screen,
      // -400% is required instead of -100% because the negative position adds to the width of the element
      transform: 'translateY(100%)',
      opacity: 0
    }),

    // animation and styles at end of transition
    animate('.5s ease-in-out', style({
      // transition the right position to 0 which slides the content into view
      transform: 'translateY(0)',
      opacity: 1

      // transition the background opacity to 0.8 to fade it in
    }))
  ]),

  // route 'leave' transition
  transition(':leave', [
    // animation and styles at end of transition
    animate('.5s ease-in-out', style({
      // transition the right position to -400% which slides the content out of view
      transform: 'translateY(100%)',
    }))
  ])
]);
